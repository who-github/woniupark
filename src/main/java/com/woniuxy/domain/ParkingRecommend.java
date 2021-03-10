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
@TableName("t_parking_recommend")
@ApiModel(value="ParkingRecommend对象", description="")
public class ParkingRecommend implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "推荐车位表")
        @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parkingId;


}
