package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.domain.Order;
import com.woniuxy.vo.GetOrderVO;
import com.woniuxy.vo.PayOrderVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

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

    // 根据订单的订单号删除订单：注意此处需要联合删除，删除订单和订单详情页
    @Delete("")
    Boolean deleteOrderAndOrderInfo(String orderNumber);

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
            "WHERE p.parking_address like '%${parkingAddress}%'")
    List<GetOrderVO> getAllOrderByAddress(@Param("parkingAddress") String parkingAddress);

    // 根据用户的订单编号，查询所有车位的所有信息
    @Select("SELECT  " +
            " u.id,u.nick_name,u.role,u.tel, " +
            " o.total, " +
            " oi.order_status,oi.create_time,oi.pay_time, " +
            " p.property_id,p.admin_id, " +
            " parki.let_id,parki.rent_id " +
            "FROM t_user u " +
            "JOIN t_order o " +
            "ON u.id = o.user_id " +
            "JOIN t_order_info oi " +
            "ON o.order_info_id = oi.order_info_id " +
            "JOIN t_parking p " +
            "ON p.id = o.parking_place_id " +
            "JOIN t_parking_info parki " +
            "ON parki.id = p.parking_info_id " +
            "WHERE oi.order_number = #{orderNumber}")
    PayOrderVO getParkByOrderNumber(String orderNumber);

}
