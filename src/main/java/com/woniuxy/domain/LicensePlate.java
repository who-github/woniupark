package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_license_plate")
@ApiModel(value="LicensePlate对象", description="")
public class LicensePlate implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "车牌id")
        @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

        @ApiModelProperty(value = "车牌图片")
        private String lecensePlateImage;

    private String drivingLicense;

        @ApiModelProperty(value = "车牌信息")
        private String licensePlate;

        @ApiModelProperty(value = "身份证")
        private String idCard;


}
