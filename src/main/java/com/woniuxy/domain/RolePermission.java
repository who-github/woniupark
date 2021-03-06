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
@TableName("t_role_permission")
@ApiModel(value="RolePermission对象", description="")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "角色-权限关联表id")
        @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

        @ApiModelProperty(value = "管理员角色id")
        private Integer rid;

        @ApiModelProperty(value = "管理员权限id")
        private Integer pid;


}
