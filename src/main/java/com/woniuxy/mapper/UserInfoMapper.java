package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.domain.UserInfo;
import com.woniuxy.vo.HandleNoPutawayVo;
import com.woniuxy.vo.HandlePutawayVo;
import com.woniuxy.vo.UserInfoVO;
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
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    // 王鹏代码：
//    UserInfoVO findUserInfoByUserId(Integer userId);//查询用户详情信息，通过用户id

    /**
     * 唐山林的代码：
     */
    @Select("SELECT ui.`name`,u.nick_name,u.tel,ui.email,ui.qq,ui.age,ui.address,ui.id_card,ui.balance,ui.head " +
            "FROM t_user u " +
            "JOIN t_user_info ui " +
            "ON u.id = ui.user_id " +
            "WHERE u.id = #{userId}")
    UserInfoVO findUserInfoByUserId(Integer userId);//通过用户id查询用户详细信息

    @Select("SELECT p.id,p.parking_number,p.parking_address,p.parking_area,p.title,r.rental_type,r.rental_price,p.end_time " +
            "FROM t_parking p " +
            "JOIN t_rental r " +
            "ON p.rental_id = r.id " +
            "JOIN t_parking_info pi " +
            "ON pi.id = p.parking_info_id " +
            "JOIN t_user u " +
            "ON u.id = pi.rent_id " +
            "WHERE p.parking_status=0 " +
            "AND u.id = #{userId}")
    List<HandlePutawayVo> findParkingInfoByUserId(Integer userId);//通过用户id查询车位已上架的信息

    @Select("SELECT p.id,p.parking_number,p.parking_address,p.parking_area,p.title,r.rental_type,r.rental_price " +
            "FROM t_parking p " +
            "JOIN t_rental r " +
            "ON p.rental_id = r.id " +
            "JOIN t_parking_info pi " +
            "ON pi.id = p.parking_info_id " +
            "JOIN t_user u " +
            "ON u.id = pi.rent_id " +
            "WHERE p.parking_status=1 " +
            "AND u.id = #{userId}")
    List<HandleNoPutawayVo> findNoParkingInfoByUserId(Integer userId);//通过用户id查询车位未上架的信息


}
