package com.woniuxy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProportyVO extends PageVO{
    @ApiModelProperty(value = "管理员表-id")
    @TableId(value = "admin_id", type = IdType.ID_WORKER)
    private Integer adminId;
    private String name;
    private String password;
    private String address;
    private double proportion;
    private double income;
}
