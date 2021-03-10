package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniuxy.domain.Parking;
import com.woniuxy.domain.Rental;
import com.woniuxy.domain.RentalStatus;
import com.woniuxy.domain.User;
import com.woniuxy.mapper.ParkingMapper;
import com.woniuxy.service.ParkingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.vo.ParkingVO;
import com.woniuxy.vo.SeckillParkingVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {

    @Resource
    private ParkingMapper parkingMapper;

    //通过车位表id查询车位表信息
    @Override
    public Parking selectParkingById(Integer id) {
        System.out.println("到这来了");
        Parking parking = parkingMapper.selectById(2);

//        QueryWrapper<Parking> wrapper = new QueryWrapper<>();
//        wrapper.eq("id",2);
//        Parking parking = parkingMapper.selectOne(wrapper);

        //返回parking对象
        return parking;
    }


    //通过车位表id联合车位详情表和车位出租状态表查询车位出租类型和出租价格
    @Override
    public Rental selectRental(Integer parkingId) {
        Rental rental = parkingMapper.selectRentalByParkingId(parkingId);
        return rental;
    }

    //通过车位id查询车位出租人昵称
    @Override
    public User selectUser(Integer parkingId) {
        User user = parkingMapper.selectUserByParkingId(parkingId);
        return user;
    }


    //通过车位表id联合车位详情表和车位出租状态表查询车位状态
    @Override
    public RentalStatus selectRentalStatus(Integer parkingId) {
        RentalStatus rentalStatus = parkingMapper.selectRentalStatusByParkingId(parkingId);
        return rentalStatus;
    }

    //添加车位
    @Override
    public void addParking(ParkingVO parkingVO) {

    }

    /**
     * 王鹏的代码：
     */
    //推荐车位
    @Override
    public List<ParkingVO> findParkingRecommend(String parkingAddress) {
        List<ParkingVO> parkingRecommends = parkingMapper.findParkingRecommend(parkingAddress);
        return parkingRecommends;
    }

    //搜索车位
    @Override
    public List<ParkingVO> findParkingByTitle(String title,String parkingAddress) {
        List<ParkingVO> parkingByTitle = parkingMapper.findParkingByTitle(title,parkingAddress);
        return parkingByTitle;
    }

    //查找秒杀车辆
    @Override
    public List<SeckillParkingVO> findSeckillParking() {
        List<SeckillParkingVO> seckillParking = parkingMapper.findSeckillParking();
        return seckillParking;
    }

    /**
     * 黄银发的代码
     */
    @Override
    public List<Parking> findByCartId(Integer id) {//根据车位id查询的车位信息
        QueryWrapper<Parking> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        List<Parking> parking = parkingMapper.selectList(wrapper);
        return parking;
    }

    @Override
    public List<Parking> allParking() {
        List<Parking> parkings = parkingMapper.selectList(null);
        return parkings;
    }


    /**
     * 唐山林的代码：
     */
    @Override
    public void setPutaway(Integer id) {//下架功能
        Parking parking = new Parking();
        parking.setParkingStatus(1);
        System.out.println("下架功能");
        UpdateWrapper<Parking> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        parkingMapper.update(parking, wrapper);
    }

    @Override
    public void setNoPutaway(Integer id) {//上架功能
        Parking parking = new Parking();
        parking.setParkingStatus(0);
        System.out.println("上架功能");
        UpdateWrapper<Parking> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        parkingMapper.update(parking, wrapper);
    }

}

