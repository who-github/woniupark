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
@TableName("t_complain")
@ApiModel(value="Complain对象", description="")
public class Complain implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "投诉表的id")
        @TableId(value = "complain_id", type = IdType.AUTO)
    private Integer complainId;

        @ApiModelProperty(value = "投诉原因")
        private String reason;

        @ApiModelProperty(value = "用户名")
        private String userName;

        @ApiModelProperty(value = "车位信息")
        private String parkingInfo;

        @ApiModelProperty(value = "联系方式")
        private String tel;

        @ApiModelProperty(value = "是否处理了投诉 1 处理了 0 没处理")
        private String status;


}
