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
@TableName("t_appraise")
@ApiModel(value="Appraise对象", description="")
public class Appraise implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "评价表-id")
        @TableId(value = "appraise_id", type = IdType.AUTO)
    private Integer appraiseId;

    private Integer userId;

    private String appraiseContent;

    private String appraiseLevel;


}
