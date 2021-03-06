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
@TableName("t_replay")
@ApiModel(value="Replay对象", description="")
public class Replay implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "回复id")
        @TableId(value = "replay_id", type = IdType.ID_WORKER)
    private Integer replayId;

        @ApiModelProperty(value = "回复内容")
        private String replayContent;

        @ApiModelProperty(value = "评论id")
        private Integer discussId;

        @ApiModelProperty(value = "用户id")
        private Integer userId;

        @ApiModelProperty(value = "回复时间")
        private Date replayTime;


}
