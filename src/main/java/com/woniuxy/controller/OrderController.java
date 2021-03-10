package com.woniuxy.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.config.AlipayTemplate;
import com.woniuxy.domain.Complain;
import com.woniuxy.domain.Order;
import com.woniuxy.domain.OrderInfo;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.ComplainService;
import com.woniuxy.service.OrderInfoService;
import com.woniuxy.service.OrderService;
import com.woniuxy.vo.GetOrderVO;
import com.woniuxy.vo.PayVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    // 注入投诉的模板
    @Resource
    private ComplainService complainService;

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
        orderInfo.setOrderNumber(UUID.randomUUID().toString());
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

    // 查询所有的订单详情
    @GetMapping("getOrderInfo")
    public Result getOrderInfo(String orderNumber){
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_number",orderNumber);
        OrderInfo orderInfo = orderInfoService.getOne(queryWrapper);
        ArrayList<OrderInfo> orderInfos = new ArrayList<>();
        orderInfos.add(orderInfo);
        return new Result(true,StatusCode.OK,"根据订单号查询订单详情成功",orderInfos);
    }

    // 删除订单信息：注意此处需要联合删除，删除订单和订单详情页
    @GetMapping("deleteOrder")
    public Result deleteOrder(String orderNumber){
        Boolean isDelete = orderService.deleteOrderAndOrderInfo(orderNumber);
        System.out.println(isDelete);
        return new Result(true,StatusCode.OK,"删除订单成功");
    }

    /**
     * 支付订单：
     * 1、修改订单状态
     * 2、支付完成之后，修改支付时间的信息
     */
    @GetMapping(value = "payOrder",produces = "text/html")
    public String payOrder(@RequestParam("orderNumber") String orderNumber) throws AlipayApiException {
//        PayVo payVo = new PayVo();
//        payVo.setBody(); // 商品描述
//        payVo.setSubject(); //订单名称
//        payVo.setOut_trade_no(); // 订单号
//        payVo.setTotal_amount(); //总金额
        PayVo payvo = orderService.payOrder(orderNumber);
        // 调用订单修改界面
        orderService.updateOrderByPayMoney(orderNumber);
        // 将返回支付宝的支付页面，需要将这个页面进行显示
        String pay = alipayTemplate.pay(payvo);
        return pay;
    }

    // 根据车位的地址查询出订单的信息
    @GetMapping("findPrakingByAddress")
    public Result findPrakingByAddress(String parkingAddress){
        System.out.println(parkingAddress);
        List<GetOrderVO> prakingByAddress = orderService.findPrakingByAddress(parkingAddress);
        return new Result(true,StatusCode.OK,"根据车位的地址查询出订单的信息",prakingByAddress);
    }

    // 查询出所有的订单地址，存放在list集合中，不重复
    @GetMapping("getAllAddress")
    public Result getAllAddress(Integer uid){
        // 根据用户id查询出所有的订单信息，包含车位地址
        List<GetOrderVO> allOrder = orderService.getAllOrder(uid);
        // 遍历allOrder集合，将其中的地址添加到set集合中
        HashSet<String> address = new HashSet<>();
        allOrder.forEach(order->{
            address.add(order.getParkingAddress());
        });
        return new Result(true,StatusCode.OK,"根据用户id查询出所有的订单地址",address);
    }

    // 评价和投诉的controller层数据
    @PostMapping("complain")
    // 前端会传输一个评价投诉的对象，包含数据：
    // 投诉原因、用户的信息、车位的信息、联系方式
    public Result complain(@RequestBody Complain complain){
        // 调用mapper层的方法进行存储数据
        boolean save = complainService.save(complain);
        return new Result(true,StatusCode.OK,"投诉成功",complain);
    }

    /**
     * @Description: 支付宝同步通知页面
     */
    @RequestMapping(value = "alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        System.out.println(("支付成功, 进入同步通知接口..."));
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayTemplate.alipay_public_key, AlipayTemplate.charset, AlipayTemplate.sign_type);
        ModelAndView mv = new ModelAndView("alipaySuccess");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 修改订单状态为支付成功，已付款; 同时新增支付流水
            orderService.updateOrderByPayMoney(out_trade_no);
            System.out.println(("********************** 支付成功(支付宝异步通知) **********************"));
            System.out.println(("* 订单号: {}"));
            System.out.println(("* 支付宝交易号: {}"));
            System.out.println(("* 实付金额: {}"));
            System.out.println("* 购买产品: {}");
            System.out.println(("***************************************************************"));
            mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
        }else {
            System.out.println(("支付, 验签失败..."));
        }
        return mv;
    }

    /**
     * @Description: 支付宝异步 通知页面
     */
    @RequestMapping(value = "alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        System.out.println(("支付成功, 进入异步通知接口..."));

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            /*valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");*/
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayTemplate.alipay_public_key, AlipayTemplate.charset, AlipayTemplate.sign_type);

        //——请在这里编写您的程序（以下代码仅作参考）——

   /* 实际验证过程建议商户务必添加以下校验：
   1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
   2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
   3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
   4、验证app_id是否为该商户本身。
   */
        //验证成功
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                orderService.updateOrderByPayMoney(out_trade_no);

                System.out.println(("********************** 支付成功(支付宝异步通知) **********************"));
                System.out.println(("* 订单号: {}"));
                System.out.println(("* 支付宝交易号: {}"));
                System.out.println(("* 实付金额: {}"));
                System.out.println("* 购买产品: {}");
                System.out.println(("***************************************************************"));
            }
            System.out.println(("支付成功..."));
        }else {//验证失败
            System.out.println(("支付, 验签失败..."));
        }
        return "success";
    }

}

