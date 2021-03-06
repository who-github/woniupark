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
@TableName("t_discuss")
@ApiModel(value="Discuss对象", description="")
public class Discuss implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "评论id")
        @TableId(value = "discuss_id", type = IdType.ID_WORKER)
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
