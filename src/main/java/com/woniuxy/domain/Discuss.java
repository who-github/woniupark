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
@TableName("t_discuss")
@ApiModel(value="Discuss对象", description="")
public class Discuss implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "评论id")
        @TableId(value = "discuss_id", type = IdType.AUTO)
    private Integer discussId;

        @ApiModelProperty(value = "用户id")
        private Integer userId;

        @ApiModelProperty(value = "评论信息")
        private String commentNews;

        @ApiModelProperty(value = "车位id")
        private Integer parkingPlaceId;

        @ApiModelProperty(value = "评论时间")
        private Date commentTime;


}
