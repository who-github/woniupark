package com.woniuxy.vo;

import lombok.Data;

import java.util.Date;

@Data
// 获取订单信息的vo类
public class GetOrderVO {
//    " u.id,u.tel,u.role,u.nick_name, " +
    private Integer id;
    private String tel;
    private Integer role;
    private String nickName;
    //            " o.total, "
    //    total: '订单总价',
    private Double total;

    //            " oi.deleted,oi.create_time,oi.order_status,oi.pay_time,oi.order_number, " +
    private Integer deleted;
    //    create_time: '订单创建时间',
    private Date createtime;
    //    order_status: '订单状态',
    private String orderStatus;
    private Date payTime;
    // order_number: '全部订单的第一个订单号',
    private String orderNumber;
    //            " p.parking_address,p.parking_image,"
    //    name: '悟空小区',
    private String parkingAddress;
    //    url: 'panner0.jpg',
    private String parkingImage;
    //            "p.parking_area,p.ownship_parking,p.parking_number "
    // parking_area: '车位面积',
    private String parkingArea;
    //  车位产权证书
    private String ownshipParking;
    //    parking_number: '车位号',
    private String parkingNumber;

}
