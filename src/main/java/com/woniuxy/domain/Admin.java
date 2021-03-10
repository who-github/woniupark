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
@TableName("t_admin")
@ApiModel(value="Admin对象", description="")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "管理员表-id")
        @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

        @ApiModelProperty(value = "管理员名称")
        private String name;

        @ApiModelProperty(value = "密码加密需要使用的盐")
        private String salt;

        @ApiModelProperty(value = "密码")
        private String password;

    private String address;

    private Double proportion;

    private Double income;


}
