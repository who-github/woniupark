package com.woniuxy.controller;


import com.woniuxy.domain.UserVo;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.service.UserInfoService;
import com.woniuxy.service.UserService;
import com.woniuxy.vo.UserInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 王鹏的代码：
     */
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("findHead")
    //获取头像图片名称
    public Result findHead(){
        Integer userId = 36;
        UserInfoVO userInfoVO = userInfoService.findUserInfo(userId);
        String head = userInfoVO.getHead();
        return new Result(true, StatusCode.OK,"获取头像名称成功",head);
    }

    /**
     * 黄银发的代码：
     */
    @PostMapping("login")//用户登录
    public Result Login(@RequestBody UserVo userVo)throws UnsupportedEncodingException {
        System.out.println(userVo);
        Result login = userService.login(userVo);
        System.out.println(login);
        return login;
    }
    @PostMapping("register")//用户注册
    public Result Register(@RequestBody UserVo userVo){
        System.out.println(userVo+"000000000000");
        if (userVo.getTelCode().equals(userVo.getCode())){
            Result result = userService.findByTel(userVo);
            return result;
        }else{
            return new Result(false,StatusCode.ERROR,"验证码错误",null);
        }
        //先查询用户，避免用户重复


    }
    @PostMapping("phone")//验证电话号码
    public Result Phone(@RequestBody String tel){
        String tell = tel.substring(0,tel.indexOf("="));

        boolean bool = userService.findByUser(tell);
        System.out.println(bool);
        if (bool==true){
            return  new Result(true,StatusCode.OK,"该电话可以使用",null);
        }else {
            return new Result(false,StatusCode.ERROR,"该电话号码已经被注册",null);
        }
    }
    @PostMapping("telCode")
    public Result TelCode(@RequestBody String tel){
        String tell = tel.substring(0,tel.indexOf("="));
        System.out.println("sss");
//        TelCode telCode = new TelCode();
//        int i = telCode.telCode(null);
        return new Result(true,StatusCode.OK,"请稍等","123123");
    }



}

