package com.woniuxy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoVO {

    @ApiModelProperty(value = "订单详细id")

    private Integer orderId;

    private String tel;

    private String parkingArea;
    private Date startTime;
    private Date endTime;
    private String orderNumber;
    private Date createTime;
    private Double total;
    private String parkingAddress;
}
