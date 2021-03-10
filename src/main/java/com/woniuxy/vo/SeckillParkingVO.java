package com.woniuxy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SeckillParkingVO implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "车位号")
    private Integer parkingNumber;

    @ApiModelProperty(value = "车位地址")
    private String parkingAddress;

    @ApiModelProperty(value = "车位产权信息")
    private String ownshipParking;

    @ApiModelProperty(value = "车位面积")
    private String parkingArea;

    @ApiModelProperty(value = "联系方式")
    private String tel;

    @ApiModelProperty(value = "车位图片")
    private String parkingImage;

    @ApiModelProperty(value = "车位状态分为：上架(0)和下架(1)")
    private Integer parkingStatus;

    @ApiModelProperty(value = "点击量")
    private Integer hits;

    @ApiModelProperty(value = "车位标题")
    private String title;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "折扣")
    private String discount;

    @ApiModelProperty(value = "出租价格")
    private Double rentalPrice;

    @ApiModelProperty(value = "出租方式")
    private String rentalType;

    //剩余时间  前端显示用
    private String countDownTime="加载中...";

    //按钮状态
    private boolean buttonStatus=true;
}
