package com.woniuxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.Parking;
import com.woniuxy.domain.Rental;
import com.woniuxy.domain.RentalStatus;
import com.woniuxy.domain.User;
import com.woniuxy.vo.ParkingVO;
import com.woniuxy.vo.SeckillParkingVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface ParkingService extends IService<Parking> {
    /**
     * 谭雨的代码：
     * @param id
     * @return
     */
    //通过车位表id查询车位表信息
    Parking selectParkingById(Integer id);

    //通过车位表id联合车位详情表和车位出租状态表查询车位出租类型和出租价格
    Rental selectRental(Integer parkingId);

    //通过车位表id联合查询查询车位状态表
    RentalStatus selectRentalStatus(Integer parkingId);

    //通过车位id查询车位出租人昵称
    User selectUser(Integer parkingId);

    //添加出租车位
    void addParking(ParkingVO parkingVO);

    /**
     * 王鹏的代码：
     */

    //推荐车位
    public List<ParkingVO> findParkingRecommend(String parkingAddress);

    //搜索车位
    public List<ParkingVO> findParkingByTitle(String title,String parkingAddress);

    //查找秒杀车辆
    public List<SeckillParkingVO> findSeckillParking();


    /**
     * 黄银发的代码：
     */
    //根据车位id查询车位信息
    public List<Parking> findByCartId(Integer id);
    //展示所有的停车位
    public  List<Parking> allParking();

    /**
     * 唐山林的代码：
     */
    void setPutaway(Integer id);//下架功能

    void setNoPutaway(Integer id);//上架功能

}
