package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.domain.Parking;
import com.woniuxy.domain.Rental;
import com.woniuxy.domain.RentalStatus;
import com.woniuxy.domain.User;
import com.woniuxy.vo.ParkingVO;
import com.woniuxy.vo.SeckillParkingVO;
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
public interface ParkingMapper extends BaseMapper<Parking> {
    /**
     * 谭雨的代码
     * @param ParkingId
     * @return
     */
    //查询出租时间类型和出租价格
    @Select("SELECT r.rental_type,r.rental_price FROM t_parking p " +
            "JOIN t_rental r " +
            "ON p.rental_id=r.id " +
            "WHERE p.id=#{id};")
    Rental selectRentalByParkingId(Integer ParkingId);


    //查询出租状态
    @Select("SELECT rs.let_status FROM t_parking p " +
            "JOIN t_rental r " +
            "ON p.rental_id=r.id " +
            "JOIN t_rental_status rs " +
            "ON rs.id=r.rental_status_id " +
            "WHERE p.id=#{id};")
    RentalStatus selectRentalStatusByParkingId(Integer ParkingId);

    //查询出租人昵称
    @Select("SELECT u.nick_name FROM t_parking p " +
            "JOIN t_parking_info pi " +
            "ON p.id=pi.parking_id " +
            "JOIN t_user u " +
            "ON pi.let_id=u.id " +
            "WHERE p.id=#{id};")
    User selectUserByParkingId(Integer ParkingId);

    /**
     * 王鹏代码：
     */
    //推荐车位
    @Select("SELECT\n" +
            "        p.id,\n" +
            "        title,\n" +
            "        parking_number,\n" +
            "        parking_address,rental_type,\n" +
            "\t\t\t\ttitle,\n" +
            "        ownship_parking,\n" +
            "        parking_area,\n" +
            "        tel,\n" +
            "        parking_image,rental_type,\n" +
            "        start_time,\n" +
            "        end_time,\n" +
            "        parking_status,\n" +
            "\t\t\t\tr.rental_price,\n" +
            "\t\t\t\tr.rental_type\n" +
            "    FROM\n" +
            "        `t_parking`AS p \n" +
            "\t\tJOIN \n" +
            "\t\t\tt_rental AS r\n" +
            "\t\tON p.id=r.id\t\t\n" +
            "    JOIN\n" +
            "       t_parking_recommend AS pr \n" +
            "    ON pr.parking_id=p.id WHERE p.parking_status=0 AND p.parking_address LIKE #{parkingAddress}\n")
    public List<ParkingVO> findParkingRecommend(String parkingAddress);


    //搜索车位
    @Select("\tSELECT\n" +
            "        p.id,\n" +
            "        title,\n" +
            "        parking_number,\n" +
            "        parking_address,\n" +
            "\t\t\t\ttitle,\n" +
            "        ownship_parking,\n" +
            "        parking_area,rental_type,\n" +
            "        tel,\n" +
            "        parking_image,\n" +
            "        start_time,\n" +
            "        end_time,\n" +
            "        parking_status,\n" +
            "\t\t\t\tr.rental_price,\n" +
            "\t\t\t\tr.rental_type\n" +
            "    FROM\n" +
            "        `t_parking`AS p \n" +
            "\t\tJOIN \n" +
            "\t\t\tt_rental AS r\n" +
            "\t\tON p.id=r.id\n" +
            "\t\tWHERE title LIKE #{title} AND p.parking_status=0 AND p.parking_address LIKE #{parkingAddress}")
    public List<ParkingVO> findParkingByTitle(String title, String parkingAddress);

    @Select("SELECT  p.id,\n" +
            "           title,\n" +
            "                    parking_number,\n" +
            "                    parking_address,\n" +
            "                    ownship_parking,\n" +
            "                   parking_area,\n" +
            "                    tel,\n" +
            "                   parking_image,\n" +
            "\t\t\t\t\t\t \n" +
            "                    parking_status,\n" +
            "            r.rental_price,\n" +
            "           r.rental_type,\n" +
            "\t\t\t\t\t s.start_time,\n" +
            "\t\t\t\t\t s.end_time,\n" +
            "\t\t\t\t\t s.discount\n" +
            "                FROM\n" +
            "                    `t_parking`AS p\n" +
            "           JOIN \n" +
            "            t_rental AS r\n" +
            "            ON p.id=r.id \n" +
            "            JOIN t_seckill AS s\n" +
            "            ON s.id = p.id")
    //查找秒杀车辆
    List<SeckillParkingVO> findSeckillParking();

    /**
     * 黄银发的代码
     */
    //根据车位id查询车位信息
    List<Parking> findByCartId(Integer id);
    //展示所有的停车位
    List<Parking> allParking();

}
