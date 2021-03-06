package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("t_order_info")
@ApiModel(value="OrderInfo对象", description="")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "订单详细id")
        @TableId(value = "order_info_id", type = IdType.ID_WORKER)
    private Integer orderInfoId;

        @ApiModelProperty(value = "订单状态")
        private String orderStatus;

        @ApiModelProperty(value = "订单创建时间")
        @TableField(fill = FieldFill.INSERT)
    private Date createTime;

        @ApiModelProperty(value = "逻辑删除")
        @TableLogic
    private Integer deleted;

        @ApiModelProperty(value = "默认为1，修改后改为2，每次修改前对比")
        @Version
    private Integer version;

        @ApiModelProperty(value = "支付时间")
        private Date payTime;

        @ApiModelProperty(value = "订单号")
        private Integer orderNumber;

        @ApiModelProperty(value = "订单总价")
        private Double total;


}
