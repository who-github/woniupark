package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.domain.Order;
import com.woniuxy.mapper.OrderInfoMapper;
import com.woniuxy.mapper.OrderMapper;
import com.woniuxy.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.vo.GetOrderVO;
import com.woniuxy.vo.PayVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        Order order = orderMapper.selectOne(wrapper);
        // 根据订单取出订单的详情id
        Integer orderInfoId = order.getOrderInfoId();
        // 删除订单详情
        int delete = orderInfoMapper.deleteById(orderInfoId);
        // 2、再次删除订单信息
        int deleteById = orderMapper.deleteById(order.getOrderId());
        // 输出删除的信息，进行展示，判断是否删除成功
        System.out.println("删除订单详情返回值："+delete+";删除订单返回值:"+deleteById);
        return null;
    }

    @Override
    public PayVo payOrder(String orderSn) {
        PayVo payVo = new PayVo();
        // payVo.setBody(); // 商品描述
        payVo.setBody("这是一个测试的商品进行支付操作");
        // payVo.setSubject(); //订单名称
        payVo.setSubject("娃哈哈");
        // payVo.setOut_trade_no(); // 订单号
        payVo.setOut_trade_no("2212");
        // payVo.setTotal_amount(); //总金额
        payVo.setTotal_amount("100");
        return payVo;
    }

    @Override
    // 根据车位的地址查询车位信息
    public List<GetOrderVO> findPrakingByAddress(String parkingAddress) {
        // 根据车位的信息查询
        List<GetOrderVO> allOrderByAddress = orderMapper.getAllOrderByAddress(parkingAddress);
        return allOrderByAddress;
    }
}
