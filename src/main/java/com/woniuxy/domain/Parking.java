package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author clk
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_parking")
@ApiModel(value="Parking对象", description="")
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "车位id")
        @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        @ApiModelProperty(value = "出租id")
        private Integer rentalId;

        @ApiModelProperty(value = "车位详细id")
        private Integer parkingInfoId;

        @ApiModelProperty(value = "物业的id")
        private Integer propertyId;

        @ApiModelProperty(value = "平台的id")
        private Integer adminId;

        @ApiModelProperty(value = "车位标题")
        private String title;

        @ApiModelProperty(value = "车位号")
        private Integer parkingNumber;

        @ApiModelProperty(value = "车位地址")
        private String parkingAddress;

        @ApiModelProperty(value = "车位产权信息")
        private String ownshipParking;

        @ApiModelProperty(value = "车位面积")
        private String parkingArea;

        @ApiModelProperty(value = "车库的经纬度")
        private String point;

        @ApiModelProperty(value = "联系方式")
        private String tel;

        @ApiModelProperty(value = "车位图片")
        private String parkingImage;

        @ApiModelProperty(value = "起始出租时间")
        private Date startTime;

        @ApiModelProperty(value = "结束出租时间")
        private Date endTime;

        @ApiModelProperty(value = "车位状态分为：上架(0)和下架(1)")
        private Integer parkingStatus;

        @ApiModelProperty(value = "点击量")
        private Integer hits;

        @ApiModelProperty(value = "出租价格表的id关联")
        private Integer rentalPriceId;

        @ApiModelProperty(value = "产权信息是否审核 1表示审核 0 没有")
        private Integer auditStatu;


}
