package com.woniuxy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data


public class ParkingInfoVO {
    //车位id
    private Integer id;
    //出租人昵称
    private String nickName;
    //出租id
    private Integer rentalPriceId;
    //车位编号
    private Integer parkingNumber;
    //车位地址
    private String parkingAddress;
    //车位产权信息
    private String ownshipParking;
    //车位面积
    private String parkingArea;
    //联系方式
    private String tel;
    //车位图片
    private String parkingImage;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    //结束出租时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    //点击量
    private Integer hits;

    //车位描述
    private String title;

//    //车位状态分为：上架（0）和下架（1）
//    private Integer parkingStatus;

    //车位出租状态
    private String letStatus;
    //出租时间类型
    private String rentalType;
    //出租价格
    private Double rentalPrice;
}
