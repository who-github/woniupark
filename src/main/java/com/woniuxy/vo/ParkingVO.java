package com.woniuxy.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 王鹏和谭雨的VO冲突
 */

@Data
public class ParkingVO {

    //车位编号
    private Integer parkingNumber;
    //车位地址数组
    private String parkingAddress;
    //车位产权信息
    private String ownshipParking;
    //车位面积
    private String parkingArea;
    //联系方式(通过用户id去数据库查)
    private String tel;
    //车位图片
    private String parkingImage;
    //起始出租时间
    private Date startTime;
    //结束出租时间
    private Date endTime;

    //车位描述
    private String title;
    //车位状态分为：上架（0）和下架（1）
    private Integer parkingStatus;
    //车位出租状态（默认未出租）
    private String letStatus;
    //出租时间类型
    private String rentalType;
    //出租价格
    private Double rentalPrice;

    //产权信息是否审核（新增默认0，审核为1）
    private Integer auditStatu;

    //hits默认为0
    private Integer hits;
    //经纬度
    private String point;
    @ApiModelProperty(value = "车位id")
    private Integer id;

    @ApiModelProperty(value = "出租id")
    private Integer rentalPriceId;

    @ApiModelProperty(value = "出租状态id")
    private Integer rentalStatusId;



}
