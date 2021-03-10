package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.*;
import com.woniuxy.dto.Result;
import com.woniuxy.mapper.*;
import com.woniuxy.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.vo.GetOrderVO;
import com.woniuxy.vo.PayOrderVO;
import com.woniuxy.vo.PayVo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    // 注入mapper层的对象
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    // 查询所有的订单信息，根据用户的id
    public List<GetOrderVO> getAllOrder(Integer uid) {
        // 调用mapper方法：根据用户id查询所有的订单信息
        List<GetOrderVO> allOrder = orderMapper.getAllOrder(uid);
        return allOrder;
    }

    @Override
    // 根据订单的id删除订单：注意此处需要联合删除，删除订单和订单详情页
    public Boolean deleteOrderAndOrderInfo(String orderNumber) {
        // 1、首先需要删除订单详情页
        // 查询出订单的订单号查询出订单的信息
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        OrderInfo orderInfo = orderInfoMapper.selectOne(wrapper);
        // 根据订单取出订单的详情id
        Integer orderInfoId = orderInfo.getOrderInfoId();
        // 删除订单详情
        int delete = orderInfoMapper.deleteById(orderInfoId);
        // 2、再次删除订单信息
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_info_id",orderInfoId);
        int deleteById = orderMapper.delete(orderQueryWrapper);
        // 输出删除的信息，进行展示，判断是否删除成功
        System.out.println("删除订单详情返回值："+delete+";删除订单返回值:"+deleteById);
        return null;
    }

    @Override
    public PayVo payOrder(String orderNumber) {
        PayVo payVo = new PayVo();
        // 根据订单号查询出订单的信息
        PayOrderVO payOrderVO = orderMapper.getParkByOrderNumber(orderNumber);
        // payVo.setBody(); // 商品描述
        payVo.setBody("这是一个测试的商品进行支付操作");
        // payVo.setSubject(); //订单名称
        payVo.setSubject(payOrderVO.getNickName());
        // payVo.setOut_trade_no(); // 订单号
        payVo.setOut_trade_no(orderNumber);
        // payVo.setTotal_amount(); //总金额
        payVo.setTotal_amount(payOrderVO.getTotal()+"");
        return payVo;
    }

    @Override
    // 根据车位的地址查询车位信息
    public List<GetOrderVO> findPrakingByAddress(String parkingAddress) {
        // 根据车位的信息查询
        List<GetOrderVO> allOrderByAddress = orderMapper.getAllOrderByAddress(parkingAddress);
        // 根据用户的id，需要判断一下
        List<GetOrderVO> getOrderVOS = new ArrayList<>();
        allOrderByAddress.forEach(order->{
            // 判断session中的数据用户id值
            if ((order.getId() == 1)) {
                getOrderVOS.add(order);
            }
        });
        return getOrderVOS;
    }

    @Override
    /**
     * 支付完成之后，需要对订单的状态进行修改，
     * 1、物业方提成10%~30%，平台方后台配置
     * 2、平台抽成10~30%，在平台方管理员后台可配置。
     * 3、出租方、平台和物业合计100%的收入金额比例
     */
    public Boolean updateOrderByPayMoney(String orderNumber) {
        // 查询出订单的订单号查询出订单的信息
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        OrderInfo orderInfo = orderInfoMapper.selectOne(wrapper);
        // 修改订单的状态
        orderInfo.setOrderStatus("已支付");
        // 需要修改平台方，物业方和出租方的信息
        // 需要在车位的信息中添加抽成的信息，两个字段，联表订单、订单详情、车位表、车位详情表进行查询
        // 根据订单编号查询出车位信息：PayOrderVO类
        PayOrderVO payOrderVO = orderMapper.getParkByOrderNumber(orderNumber);
        System.out.println("PayOrderVO类");
        System.out.println(payOrderVO);
        // 根据平台的id查询出平台的信息
        Admin admin = adminMapper.selectById(payOrderVO.getAdminId());
        System.out.println("平台完成订单之前的数据："+admin);//测试
        // 增加平台的收入
        if (admin.getIncome()==null){
            admin.setIncome(0.0);
        }
        admin.setIncome(admin.getIncome()+(payOrderVO.getTotal()*admin.getProportion()));
        System.out.println("平台完成订单之后的数据："+admin);//测试
        adminMapper.updateById(admin);
        // 增加物业的收入
        Admin property = adminMapper.selectById(payOrderVO.getPropertyId());
        System.out.println("物业完成订单之前的数据："+property);//测试
        if (property.getIncome()==null){
            property.setIncome(0.0);
        }
        property.setIncome(property.getIncome()+(payOrderVO.getTotal()*property.getProportion()));
        System.out.println("物业完成订单之后的数据："+property);//测试
        adminMapper.updateById(property);
        // 增加出租方的收入
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",payOrderVO.getLetId());
        UserInfo letUser = userInfoMapper.selectOne(queryWrapper);
        System.out.println("出租方完成订单之前的数据："+letUser);//测试
        letUser.setBalance(letUser.getBalance()+((payOrderVO.getTotal()*(1-admin.getProportion()-property.getProportion()))));
        System.out.println("出租方完成订单之后的数据："+letUser);//测试
        userInfoMapper.updateById(letUser);
        // 减少租客余额信息
        QueryWrapper<UserInfo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id",payOrderVO.getRentId());
        UserInfo rentUser = userInfoMapper.selectOne(queryWrapper);
        System.out.println("租客："+rentUser);
        System.out.println("租客完成订单之前的数据："+rentUser);//测试
        letUser.setBalance(rentUser.getBalance()-((payOrderVO.getTotal()*admin.getProportion())+(payOrderVO.getTotal()*property.getProportion())));
        System.out.println("租客完成订单之后的数据："+rentUser);//测试
        userInfoMapper.updateById(letUser);
        // 设置状态保存或数据库
        orderInfoMapper.updateById(orderInfo);
        return null;
    }


}
