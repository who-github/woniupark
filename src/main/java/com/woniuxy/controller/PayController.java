package com.woniuxy.controller;

import com.alipay.api.AlipayApiException;
import com.woniuxy.config.AlipayTemplate;
import com.woniuxy.service.OrderService;
import com.woniuxy.vo.PayVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PayController {
    @Resource
    private OrderService orderService;

    @Resource
    private AlipayTemplate alipayTemplate;

    /**
     * 支付订单：
     * 1、修改订单状态
     * 2、支付完成之后，修改支付时间的信息
     */
    @GetMapping(value = "payOrder")
    public void payOrder(@RequestParam("orderNumber") String orderNumber, HttpServletResponse response) throws AlipayApiException, IOException {

//        PayVo payVo = new PayVo();
//        payVo.setBody(); // 商品描述
//        payVo.setSubject(); //订单名称
//        payVo.setOut_trade_no(); // 订单号
//        payVo.setTotal_amount(); //总金额
        PayVo payvo = orderService.payOrder(orderNumber);

        // 将返回支付宝的支付页面，需要将这个页面进行显示
        String pay = alipayTemplate.pay(payvo);

        System.out.println(pay);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(pay);


    }


}
