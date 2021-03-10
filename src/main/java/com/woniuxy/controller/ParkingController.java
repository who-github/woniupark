package com.woniuxy.controller;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniuxy.domain.Parking;
import com.woniuxy.domain.Rental;
import com.woniuxy.domain.RentalStatus;
import com.woniuxy.domain.User;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.ParkingService;
import com.woniuxy.utils.LatitudeUtils;
import com.woniuxy.vo.PageParkingVO;
import com.woniuxy.vo.ParkingInfoVO;
import com.woniuxy.vo.ParkingVO;
import com.woniuxy.vo.SeckillParkingVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/parking")
public class ParkingController {

    //获取地址
    private String address;
    //获取出租方上传图片名称
    private String parkingImage;

    @Resource
    private ParkingService parkingService;

    //给车位详情表
    @GetMapping("info/{id}")
    public Result<ParkingInfoVO> parkInfo(@PathVariable Integer id){
        System.out.println(id);
        //获取车位信息
        Parking parking = parkingService.selectParkingById(id);
        System.out.println(parking.getStartTime());
        System.out.println(parking.getEndTime());

        //获取车位出租类型和车位出租价格
        Rental rental = parkingService.selectRental(id);
        //获取车位出租状态
        RentalStatus rentalStatus = parkingService.selectRentalStatus(id);
        //获取车位出租人昵称
        User user = parkingService.selectUser(id);
        System.out.println(user);

        //new一个parkingInfoVO给前端传递值
        ParkingInfoVO parkingInfoVO = new ParkingInfoVO();

        //为parkingInfoVO赋值
        parkingInfoVO.setId(parking.getId());

        parkingInfoVO.setStartTime(parking.getStartTime());//租车位开始时间
        parkingInfoVO.setEndTime(parking.getEndTime());//租车位结束时间


        parkingInfoVO.setParkingNumber(parking.getParkingNumber());//车位编号
        parkingInfoVO.setParkingAddress(parking.getParkingAddress());//车位地址
        parkingInfoVO.setHits(parking.getHits());//点击量
        parkingInfoVO.setParkingArea(parking.getParkingArea());//车位面积
        parkingInfoVO.setTel(parking.getTel());//出租人联系方式
        parkingInfoVO.setParkingImage(parking.getParkingImage());//车位照片
        parkingInfoVO.setTitle(parking.getTitle());//车位描述

        parkingInfoVO.setRentalType(rental.getRentalType());//车位出租类型（时/日/周）
        parkingInfoVO.setRentalPrice(rental.getRentalPrice());//车位出租价格

        parkingInfoVO.setNickName(user.getNickName());//出租人昵称

        parkingInfoVO.setLetStatus(rentalStatus.getLetStatus());//车位出租状态

        return new Result(true, StatusCode.OK,"车位详情页数据传递",parkingInfoVO);

    }


    //新添加车位
    @PostMapping("join")
    public Result addParking(@RequestBody ParkingVO parkingVO){


        //地址
        parkingVO.setParkingAddress(address);
        //去掉地址中的下划线
        String p=address.replace("/","");

        //获取到经纬度
        String point= LatitudeUtils.getPoint(p);
        System.out.println("....................");
        System.out.println(point);
        System.out.println("....................");
        //图片
        parkingVO.setParkingImage(parkingImage);
//        System.out.println("..............................");
//        System.out.println(this.parkingImage);
//        System.out.println("..............................");
        //车位状态默认未上架，默认为0
        parkingVO.setParkingStatus(0);
        //出租状态默认未出租
        parkingVO.setLetStatus("未出租");
        //审核状态(默认为0：未审核)
        parkingVO.setAuditStatu(0);

        //点击量（初始为0）
        parkingVO.setHits(0);

        //出租类型选择
        if(parkingVO.getRentalType().equals("选项1")){
            parkingVO.setRentalType("按小时租");
        }else if (parkingVO.getRentalType().equals("选项2")){
            parkingVO.setRentalType("按天租");
        }else{
            parkingVO.setRentalType("按周租");
        }
        //title默认跟地址一样
        parkingVO.setTitle(parkingVO.getParkingAddress());
        parkingService.addParking(parkingVO);
        System.out.println(parkingVO);
        return new Result(true,StatusCode.OK,"新增车位成功");
    }
    //地址信息传递
    @PostMapping("address")
    public Result getAddress(@RequestBody String data){

        //将字符串转码成汉字（不可以）
        String decodeStr="";
        try{
            decodeStr = URLDecoder.decode(data, "utf-8");
            //替换
            address=decodeStr.replace("="," ");


        }catch (Exception e){
            e.printStackTrace();
        }
        //输出转换后的值
        System.out.println(address);
        System.out.println(this.address);

        return new Result(true,StatusCode.OK,"地址获取成功");
    }

    //上传车位图片
    @RequestMapping("picture")
    public Result addPicture(MultipartFile file){

        System.out.println("进来了");
        //上传文件路径到硬盘
        String path="D:/MyFile/vue-cli/part/static/images-addPark".replace("/", File.separator);

        File file1=new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //旧文件名
        String oldName = file.getOriginalFilename();
        System.out.println("旧名字"+oldName);

        //避免重复（加uuid和系统当前时间）
        //生产uuid，并将uuid中间的“-”替换成”“
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取系统时间
        long currentTimeMillis = System.currentTimeMillis();

        //得到最终上传文件名
        parkingImage=uuid+currentTimeMillis+oldName;
        System.out.println("新名字"+parkingImage);
        try {
            //执行上传操作file
            file.transferTo(new File(path,parkingImage));
            System.out.println("上传成功");
            return new Result(true,StatusCode.OK,"图片上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return new Result(false, StatusCode.ERROR,"图片上传失败");
        }
    }


    /**
     * 王鹏代码：
     */
    //推荐车位
    @PostMapping("findParkingRecommend")
    public Result findParkingRecommend(String parkingAddress){
        System.err.println(parkingAddress+"parkingAddress");
        Random random = new Random();
        //分页
//        PageHelper.startPage(1, 2);
        if(parkingAddress==null){
            parkingAddress="";
        }
        List<ParkingVO> parkingRecommend = parkingService.findParkingRecommend("%"+parkingAddress+"%");
//        PageInfo pageInfo = new PageInfo(parkingRecommend);
//        System.out.println(pageInfo+"-----");
        List<ParkingVO> parkingVOList = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < 4; i++) {
            //产生随机数
            num=random.nextInt(parkingRecommend.size());
            if(!parkingVOList.contains(parkingRecommend.get(num))){
                parkingVOList.add(parkingRecommend.get(num));
            }else {
                i--;
            }
        }
        return new Result(true, StatusCode.OK,"推荐车位",parkingVOList);
    }

    //全部车位+条件搜索
    @PostMapping("findParkingByTitle")
    public Result findParkingByTitle(@RequestBody PageParkingVO pageParkingVO){
        if(pageParkingVO.getParkingAddress()==null){
            pageParkingVO.setParkingAddress("");
        }
        if(pageParkingVO.getTitle()==null){
            pageParkingVO.setTitle("");
        }
        PageHelper.startPage(pageParkingVO.getCurrent(), 8);
        List<ParkingVO> parkingByTitle = parkingService.findParkingByTitle("%"+pageParkingVO.getTitle()+"%","%"+pageParkingVO.getParkingAddress()+"%");
        //分页
        PageInfo pageInfo = new PageInfo(parkingByTitle);
        return new Result(true, StatusCode.OK,"搜索成功",pageInfo);
    }

    //秒杀车位
    @PostMapping("seckillParking")
    public Result seckillParking(){
        Random random = new Random();
        List<SeckillParkingVO> seckillParking = parkingService.findSeckillParking();
        List<SeckillParkingVO> parkingVOList = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < 3; i++) {
            //产生随机数
            num=random.nextInt(seckillParking.size());
            if(!parkingVOList.contains(seckillParking.get(num))){
                parkingVOList.add(seckillParking.get(num));
            }else {
                i--;
            }
        }
        System.err.println(seckillParking);
        return new Result(true, StatusCode.OK,"秒杀车位查询成功",parkingVOList);
    }

    /**
     * 黄银发的代码
     */
    @PostMapping("info")
    public void parkInfo(String num, HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=utf-8");
        System.out.println(num+"前端传的");
        Integer id=Integer.parseInt(num);
        List<Parking> byCartId = parkingService.findByCartId(id);

        response.getWriter().write(JSON.toJSONString(byCartId));
    }
    @PostMapping("allParking")
    public Result AllParking(){
        List<Parking> parkings = parkingService.allParking();
        System.out.println(parkings);
        return new Result(true, StatusCode.OK,"查询成功",parkings);
    }

}

