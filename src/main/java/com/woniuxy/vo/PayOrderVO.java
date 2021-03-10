package com.woniuxy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
// 支付时需要使用到的数据库
public class PayOrderVO {

//    用户id
    private Integer id;
//    昵称
    private String nickName;
//    出租或者未出租
    private Integer role;
//    电话
    private String tel;
//    订单总价
    private double total;
//    订单状态
    private String orderStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    创建时间
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    支付时间
    private Date payTime;
//    平台抽成
    private Integer adminId;
//    物业抽成
    private Integer propertyId;
//    出租方id
    private Integer letId;
//    租客id
    private Integer rentId;

}
