package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.vo.GetOrderVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import com.woniuxy.vo.OrderInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface OrderMapper extends BaseMapper<Order> {

    // 查询所有的订单信息，根据用户的id
    @Select("SELECT  " +
            " u.id,u.tel,u.role,u.nick_name, " +
            " o.total, " +
            " oi.deleted,oi.create_time,oi.order_status,oi.pay_time,oi.order_number, " +
            " p.parking_address,p.parking_image,p.parking_area,p.ownship_parking,p.parking_number " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON u.id = o.user_id " +
            "JOIN t_order_info oi " +
            "ON o.order_info_id = oi.order_info_id " +
            "JOIN t_parking p " +
            "ON o.parking_place_id = p.id " +
            "JOIN t_parking_info pin " +
            "ON pin.id = p.parking_info_id " +
            "WHERE u.id =#{uid}")
    List<GetOrderVO> getAllOrder(Integer uid);

    // 根据订单的id删除订单：注意此处需要联合删除，删除订单和订单详情页
    Boolean deleteOrderAndOrderInfo(Integer id);

    // 根据订单的id，修改订单详情表的订单状态
    Boolean updateOrderInfo(Integer rid);

    // 根据车位的地址查询订单信息
    @Select(" SELECT  " +
            " u.id,u.tel,u.role,u.nick_name, " +
            " o.total, " +
            " oi.deleted,oi.create_time,oi.order_status,oi.pay_time,oi.order_number, " +
            " p.parking_address,p.parking_image,p.parking_area,p.ownship_parking,p.parking_number " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON u.id = o.user_id " +
            "JOIN t_order_info oi " +
            "ON o.order_info_id = oi.order_info_id " +
            "JOIN t_parking p " +
            "ON o.parking_place_id = p.id " +
            "JOIN t_parking_info pin " +
            "ON pin.id = p.parking_info_id " +
            "WHERE p.parking_address =#{parking_address};")
    List<GetOrderVO> getAllOrderByAddress(String parkingAddress);



    @Select("SELECT o.order_id,p.tel,p.parking_area,p.start_time,p.end_time,oin.order_number,oin.create_time,oin.total,p.parking_address " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON o.user_id = u.id " +
            "JOIN t_parking p " +
            "ON p.id =o.parking_place_id " +
            "JOIN t_parking_info pin " +
            "ON u.id =pin.parking_id  " +
            "JOIN t_order_info oin " +
            "ON oin.order_info_id = o.order_id " +
            "${ew.customSqlSegment}")
    Page<OrderInfoVO> findUnPayOrder(Page<OrderInfoVO> page,@Param(Constants.WRAPPER) QueryWrapper<OrderInfoVO> wrapper);

    @Select("SELECT o.order_id,p.tel,p.parking_area,p.start_time,p.end_time,oin.order_number,oin.create_time,oin.total,p.parking_address " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON o.user_id = u.id " +
            "JOIN t_parking p " +
            "ON p.id =o.parking_place_id " +
            "JOIN t_parking_info pin " +
            "ON u.id =pin.parking_id  " +
            "JOIN t_order_info oin " +
            "ON oin.order_info_id = o.order_id " +
            "${ew.customSqlSegment}")
    Page<OrderInfoVO> findOnPayOrder(Page<OrderInfoVO> page,@Param(Constants.WRAPPER) QueryWrapper<OrderInfoVO> wrapper);

    @Select("SELECT o.order_id,p.tel,p.parking_area,p.start_time,p.end_time,oin.order_number,oin.create_time,oin.total,p.parking_address " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON o.user_id = u.id " +
            "JOIN t_parking p " +
            "ON p.id =o.parking_place_id " +
            "JOIN t_parking_info pin " +
            "ON u.id =pin.parking_id  " +
            "JOIN t_order_info oin " +
            "ON oin.order_info_id = o.order_id " +
            "WHERE oin.order_number =#{ordernumber} ")
    List<OrderInfoVO> deleteOrderByNumber(String ordernumber);

        @Select("SELECT o.order_id,p.tel,p.parking_area,p.start_time,p.end_time,oin.order_number,oin.create_time,oin.total,p.parking_address " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON o.user_id = u.id " +
            "JOIN t_parking p " +
            "ON p.id =o.parking_place_id " +
            "JOIN t_parking_info pin " +
            "ON u.id =pin.parking_id  " +
            "JOIN t_order_info oin " +
            "ON oin.order_info_id = o.order_id ${ew.customSqlSegment}")
    Page<OrderInfoVO> selectAllOrder(Page<OrderInfoVO> page,@Param(Constants.WRAPPER) QueryWrapper<OrderInfoVO> wrapper);
}
