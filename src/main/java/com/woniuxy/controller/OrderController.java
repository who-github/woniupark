package com.woniuxy.controller;

import com.alipay.api.AlipayApiException;
import com.woniuxy.config.AlipayTemplate;
import com.woniuxy.domain.Order;
import com.woniuxy.domain.OrderInfo;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.OrderInfoService;
import com.woniuxy.service.OrderService;
import com.woniuxy.vo.GetOrderVO;
import com.woniuxy.vo.PayVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    // 注入service的对象
    @Resource
    private OrderService orderService;
    // 注入订单详情的service对象
    @Resource
    private OrderInfoService orderInfoService;
    // 注入支付的模板
    @Resource
    private AlipayTemplate alipayTemplate;

    /**
     * 生成订单的信息
     * 1.首先生成订单详情页
     * 2、在生成订单详情页后进行订单表的增加
     */
    @GetMapping("addOrder")
    // 需要传入的参数为：车位的id
    public Result addOrder(Integer parkingId){
        // 根据用户传入的订单信息添加订单详情表信息
        OrderInfo orderInfo = new OrderInfo();
        Date createTime = new Date();
        // 订单详情属性赋值
        // 订单号：
        orderInfo.setOrderNumber(createTime+UUID.randomUUID().toString());
        orderInfo.setOrderStatus("未支付");
        orderInfo.setCreateTime(createTime);
        orderInfo.setTotal(100.0);
        boolean saveOrderInfo = orderInfoService.save(orderInfo);
        // 添加订单的信息：
        Order order = new Order();
        // 订单属性设置:save保存之后，可以直接获取对象的id
        order.setOrderInfoId(orderInfo.getOrderInfoId());
        order.setParkingPlaceId(1);
        order.setTotal(orderInfo.getTotal());
        order.setUserId(1);
        // 保存用户信息
        boolean saveOrder = orderService.save(order);
        return new Result(true,StatusCode.OK,"添加订单成功",order);
    }

    // 点击查询我的订单：调用service层的方法，完成查询所有订单的信息
    @GetMapping("getAllOrder")
    public Result getAllOrder(Integer uid){
        // 由于mapper层的数据是生成的全部数据：包含逻辑删除的delete，需要前端判断一下
        List<GetOrderVO> allOrder = orderService.getAllOrder(uid);
        return new Result(true, StatusCode.OK,"查询所有信息成功",allOrder);
    }

    // 删除订单信息：注意此处需要联合删除，删除订单和订单详情页
    @GetMapping("deleteOrder")
    public Result deleteOrder(String orderNumber){
        Boolean isDelete = orderService.deleteOrderAndOrderInfo(orderNumber);
        System.out.println(isDelete);
        return new Result(true,StatusCode.OK,"删除订单成功");
    }

    // 修改订单的信息：根据订单的模型进行重新修改
    @GetMapping("updateOrder")
    public Result updateOrder(){
        //
        return new Result();
    }

    /**
     * 支付订单：
     * 1、修改订单状态
     * 2、支付完成之后，修改支付时间的信息
     */
    @GetMapping(value = "payOrder",produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
//        PayVo payVo = new PayVo();
//        payVo.setBody(); // 商品描述
//        payVo.setSubject(); //订单名称
//        payVo.setOut_trade_no(); // 订单号
//        payVo.setTotal_amount(); //总金额
        PayVo payvo = orderService.payOrder(orderSn);
        // 将返回支付宝的支付页面，需要将这个页面进行显示
        String pay = alipayTemplate.pay(payvo);
        System.out.println(pay);
        return pay;
    }

    @GetMapping("payed/notify")
    public Result payNotify(){
        return new Result(true,StatusCode.OK,"回调界面出现");
    }


    // 根据车位的地址查询出订单的信息
    @GetMapping("findPrakingByAddress")
    public Result findPrakingByAddress(String parkingAddress){
        List<GetOrderVO> prakingByAddress = orderService.findPrakingByAddress(parkingAddress);
        return new Result(true,StatusCode.OK,"根据车位的地址查询出订单的信息",prakingByAddress);
    }

}

