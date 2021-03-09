package com.woniuxy.service;

import com.woniuxy.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.Parking;
import com.woniuxy.vo.GetOrderVO;
import com.woniuxy.vo.PayVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface OrderService extends IService<Order> {

    // 查询所有的订单信息，根据用户的id
    List<GetOrderVO> getAllOrder(Integer uid);

    // 根据订单的订单号删除订单：注意此处需要联合删除，删除订单和订单详情页
    Boolean deleteOrderAndOrderInfo(String orderNumber);

    // 支付订单信息：
    PayVo payOrder(String orderSn);

    // 根据车位的地址查询车位信息
    List<GetOrderVO> findPrakingByAddress(String parkingAddress);

}
