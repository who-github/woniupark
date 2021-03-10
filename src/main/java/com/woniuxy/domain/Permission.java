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
@TableName("t_permission")
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "管理员权限表-id")
        @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

        @ApiModelProperty(value = "菜单项")
        private String element;

        @ApiModelProperty(value = "路径")
        private String url;

        @ApiModelProperty(value = "菜单等级")
        private Integer level;

        @ApiModelProperty(value = "父类菜单等级")
        private Integer pid;


}
