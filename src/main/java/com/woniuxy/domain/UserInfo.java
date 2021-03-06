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
@TableName("t_user_info")
@ApiModel(value="UserInfo对象", description="")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_info_id", type = IdType.ID_WORKER)
    private Integer userInfoId;

        @ApiModelProperty(value = "关联用户表的id：t_user")
        private Integer userId;

        @ApiModelProperty(value = "关联车位表的id")
        private Integer parkingId;

        @ApiModelProperty(value = "可以为null")
        private String email;

        @ApiModelProperty(value = "性别")
        private String sex;

    private Date applyYtime;

    private String qq;

    private String name;

    private Date birthday;

    private String address;

    private String idCard;

    private Double balance;

    private String description;

    private String status;

    private String head;

    private Integer age;

    private String marriage;

    private Integer credit;


}
