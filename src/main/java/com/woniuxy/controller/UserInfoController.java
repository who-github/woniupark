package com.woniuxy.controller;


import com.woniuxy.domain.UserInfo;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.ParkingService;
import com.woniuxy.service.UserInfoService;
import com.woniuxy.vo.HandleNoPutawayVo;
import com.woniuxy.vo.HandlePutawayVo;
import com.woniuxy.vo.SetPassword;
import com.woniuxy.vo.UserInfoVO;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@RestController
@CrossOrigin
@RequestMapping("/userinfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ParkingService parkingService;


    @GetMapping("/findDetails")
    //查看登录用户所有信息
    public Result findUserInfo(Integer userId){
        System.out.println(userId);
        UserInfoVO userInfo = userInfoService.findUserInfo(userId);
        System.out.println(userInfo);
        return new Result(true, StatusCode.OK,"查询用户详细成功",userInfo);
    }

    @PostMapping("setHead")
    //修改头像
    public Result setHead(MultipartFile file){

        Integer userId = 36;
        Integer integer = userInfoService.updateUserHead(userId, file);
        if (integer==0){
            return new Result(true, StatusCode.OK,"修改头像失败");
        }else{
            return new Result(true, StatusCode.OK,"修改头像成功");
        }
    }

    @GetMapping("findHead")
    //获取头像图片名称
    public Result findHead(){
        Integer userId = 36;
        UserInfoVO userInfoVO = userInfoService.findUserInfo(userId);
        String head = userInfoVO.getHead();
        return new Result(true,StatusCode.OK,"获取头像名称成功",head);
    }

    @PostMapping("setNickname")
    //修改昵称
    public Result updateUserNickname(@RequestBody Map<String,Object> map){
        Integer userId = Integer.parseInt(map.get("userId").toString());
        Integer integer = userInfoService.updateUserNickname(userId, map.get("nickname").toString());
        return new Result(true, StatusCode.OK,"修改昵称成功");
    }

    @GetMapping("setTel")
    //修改电话
    public Result updateUserTel(Integer userId,String tel){
        Integer integer = userInfoService.updateUserTel(userId, tel);
        return new Result(true,StatusCode.OK,"修改电话成功",integer);
    }

    @GetMapping("setEmail")
    //修改邮箱
    public Result updateUserEmail(Integer userId,String email){
        Integer integer = userInfoService.updateUserEmail(userId, email);
        return new Result(true,StatusCode.OK,"修改邮箱成功",integer);
    }

    @GetMapping("setQQ")
    //修改QQ
    public Result updateUserQQ(Integer userId,String QQ){
        Integer integer = userInfoService.updateUserQQ(userId, QQ);
        return new Result(true,StatusCode.OK,"修改电话成功",integer);
    }

    @PostMapping("setPassword")
    //修改密码
    public Result updateUserPassword(@RequestBody SetPassword setPassword){
        Integer integer = userInfoService.updateUserPassword(setPassword.getUserId(), setPassword.getPassword(), setPassword.getNewpassword());
        if (integer!=0){
            return new Result(true,StatusCode.OK,"修改密码成功",integer);
        }else{
            return new Result(false,StatusCode.OK,"原密码错误",integer);
        }
    }

    @GetMapping("findUsername")
    //查用户真实名字
    public Result findUsername(Integer userId){
        String nickname = userInfoService.findUsername(userId);
        return new Result(true,StatusCode.OK,"查询用户真实名字成功",nickname);
    }

    @PostMapping("uploadUserInfo")//上传身份证图片信息
    public Result updateUserInfo(MultipartFile file){
        //填用户id和上传路径
        Integer integer = userInfoService.updateUserInfo(36,file);
        if (integer!=0){
            return new Result(true,StatusCode.OK,"认证成功");
        }else{
            return new Result(false,StatusCode.ERROR,"上传身份证正面照片");
        }

    }

    @GetMapping("findCard")
    //识别成功拿身份信息
    public Result findCard(Integer userId){
        System.out.println("进入"+userId);
        UserInfo userCard = userInfoService.findCard(userId);
        if (StringUtils.isEmpty(userCard.getName())) {
            return new Result(false,StatusCode.ERROR,"请上传身份证正面图片");
        }else{
            return new Result(true,StatusCode.OK,"拿到身份数据",userCard);
        }

    }

    @GetMapping("handlePutaway")//已上架的车位信息
    public Result findParkingInfoByUserId(){
        Integer userId=1;
        List<HandlePutawayVo> parkingInfo = userInfoService.findParkingInfoByUserId(userId);
        if (ObjectUtils.isEmpty(parkingInfo)) {
            return new Result(false, StatusCode.OK,"暂无");
        }else{
            return new Result(true, StatusCode.OK,"查询成功",parkingInfo);
        }
    }

    @GetMapping("handleNoPutaway")//未上架的车位信息
    public Result findNoParkingInfoByUserId(){
        Integer userId=1;
        List<HandleNoPutawayVo> parkingInfo = userInfoService.findNoParkingInfoByUserId(userId);
        if (ObjectUtils.isEmpty(parkingInfo)) {
            return new Result(false, StatusCode.OK,"暂无");
        }else{
            return new Result(true, StatusCode.OK,"查询成功",parkingInfo);
        }
    }

    @GetMapping("setPutaway")//下架功能
    public Result setPutaway( Integer id){
        System.out.println(id);
        System.out.println("下架功能1");
        parkingService.setPutaway(id);
        return new Result();
    }

    @GetMapping("setNoPutaway")//上架功能
    public Result setNoPutaway( Integer id){
        System.out.println(id);
        System.out.println("上架功能1");
        parkingService.setNoPutaway(id);
        return new Result();
    }
}

