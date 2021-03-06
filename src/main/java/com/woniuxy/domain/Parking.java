package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_parking")
@ApiModel(value="Parking对象", description="")
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "车位id")
        @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

        @ApiModelProperty(value = "出租id")
        private Integer rentalPriceId;

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

        @ApiModelProperty(value = "起始出租时间")
        private Date startTime;

        @ApiModelProperty(value = "结束出租时间")
        private Date endTime;

        @ApiModelProperty(value = "车位状态分为：上架(0)和下架(1)")
        private Integer parkingStatus;


}
