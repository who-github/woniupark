package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
