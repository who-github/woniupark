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
@TableName("t_rental")
@ApiModel(value="Rental对象", description="")
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "出租价格id")
        @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

        @ApiModelProperty(value = "出租状态id")
        private Integer rentalStatusId;

        @ApiModelProperty(value = "出租方式")
        private String rentalType;

        @ApiModelProperty(value = "出租价格")
        private Double rentalPrice;


}
