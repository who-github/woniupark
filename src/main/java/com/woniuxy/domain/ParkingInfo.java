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
@TableName("t_parking_info")
@ApiModel(value="ParkingInfo对象", description="")
public class ParkingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "车位详细id")
        @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

        @ApiModelProperty(value = "车位id")
        private Integer parkingId;

        @ApiModelProperty(value = "出租方id")
        private Integer letId;

        @ApiModelProperty(value = "车牌id")
        private Integer lecensePlateId;

        @ApiModelProperty(value = "承租方id")
        private Integer rentId;

        @ApiModelProperty(value = "出租状态id")
        private Integer rentalStatusId;


}
