package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.domain.Admin;
import com.woniuxy.domain.Complain;
import com.woniuxy.domain.Parking;
import com.woniuxy.domain.User;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.*;
import com.woniuxy.utils.JWTUtil;
import com.woniuxy.vo.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

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
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ParkingMapper parkingMapper;

    @Resource
    private ComplainMapper complainMapper;

    @RequestMapping("login")
    public Result Login(@RequestBody AdminVO adminVO, HttpSession httpSession){
        //根据前端姓名查询数据库 比对密码
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name", adminVO.getName());
        Admin adminDB = adminMapper.selectOne(wrapper);
        System.out.println(adminDB);
        if(!ObjectUtils.isEmpty(adminDB)){//如果数据库查询不为空 做密码比对
            if(adminDB.getPassword().equals(adminDB.getPassword())){//如果密码匹配正确
                //创建jwt并放回
                HashMap<String,String> map = new HashMap<>();
                map.put("username",adminDB.getName());
                String jwtToken = JWTUtil.createToken(map);
                //保存用户数据到redis
                //单点登录
                httpSession.setAttribute("loginUserId", adminDB.getAdminId());
                ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
                valueOperations.set("loginUser:" + adminDB.getAdminId(), httpSession.getId());
                //保存角色名  后面用来查询role
                valueOperations.set("username",adminDB.getName());
                return new Result(true,StatusCode.OK,"登录成功",jwtToken);
            }
            return new Result(false,StatusCode.ERROR,"密码错误！");
        }else {
            return new Result(false, StatusCode.UNKNOWNERROR, "当前用户尚未注册");
        }
    }

    //查询角色
    @RequestMapping("findRole")
    public Result Login(){
        String username = stringRedisTemplate.opsForValue().get("username");
        String roleName = adminMapper.findRoleByName(username);
        return new Result<>(true, StatusCode.OK,"查询角色成功",roleName);
    }

    //查询所有租客
    @RequestMapping("findTanant/{pageindex}")
    public Result FindTanant(@PathVariable Integer pageindex){
        QueryWrapper<TanantVO> wrapper = new QueryWrapper<>();
        wrapper.eq("role","1");
        Page<TanantVO> page = new Page<>(pageindex, 2);
        Page<TanantVO> tanantList = userMapper.findAllTanant(page,wrapper);

        return new Result(true,StatusCode.OK,"查询租客成功！",tanantList);
    }

    //根据姓名查询单个租客信息
    @RequestMapping("findTanantByName/{username}")
    public Result findTanantByName(@PathVariable String username){

        TanantVO tanant= userMapper.findTanantByName(username);
        return new Result(true,StatusCode.OK,"根据姓名查询成功！",tanant);
    }

    //单个删除租客信息
    @RequestMapping("deleteTanantByID/{id}")
    public Result deleteTanantByID(@PathVariable int id){

        userMapper.deleteById(id);
        return new Result(true,StatusCode.OK,"根据id删除成功！");
    }

    //批量删除租客信息
    @RequestMapping("deleteTanantByIDs")
    public Result deleteTanantByIDs(@RequestBody List<Integer> ids){
        ids.forEach(id->{
            userMapper.deleteById(id);
        });
        return new Result(true,StatusCode.OK,"批量删除成功！");
    }

    //查询所有租客
    @RequestMapping("findAllLesser/{pageindex}")
    public Result findAllLesser(@PathVariable Integer pageindex){
        QueryWrapper<TanantVO> wrapper = new QueryWrapper<>();
        wrapper.eq("role","0");
        Page<TanantVO> page = new Page<>(pageindex, 2);
        Page<TanantVO> tanantList = userMapper.findAllLesser(page,wrapper);
        return new Result(true,StatusCode.OK,"查询租客成功！",tanantList);
    }

    //查询所有物业
    @RequestMapping("findAllPeoperty/{pageindex}")
    public Result findAllPeoperty(@PathVariable Integer pageindex){
        QueryWrapper<ProportyVO> wrapper = new QueryWrapper<>();
        wrapper.eq("t_admin_role.rid","2");
        Page<ProportyVO> page = new Page<>(pageindex, 2);
        Page<ProportyVO> proportyVOPage = adminMapper.findAllPeoperty(page,wrapper);
        return new Result(true,StatusCode.OK,"查询所有物业成功！",proportyVOPage);
    }

    //查询物业根据名称
    @RequestMapping("findPeopertyByName/{username}")
    public Result findPeopertyByName(@PathVariable String username){
        ProportyVO proporty = adminMapper.findPeopertyByName(username);
        return new Result(true,StatusCode.OK,"查询物业成功！",proporty);
    }

    //单个根据id删除物业信息
    @RequestMapping("deletePeopertyByID/{id}")
    public Result deletePeopertyByID(@PathVariable int id){
        adminMapper.deleteById(id);
        return new Result(true,StatusCode.OK,"根据id删除成功！");
    }

    //批量删除物业信息
    @RequestMapping("deletePeopertyByIDs")
    public Result deletePeopertyByIDs(@RequestBody List<Integer> ids){
        ids.forEach(id->{
            adminMapper.deleteById(id);
        });
        return new Result(true,StatusCode.OK,"批量物业删除成功！");
    }

    //单个根据id修改物业提成比列
    @RequestMapping("updatePeopertyByID")
    public Result deletePeopertyByID(@RequestBody ProportionVO proportionVO){
        System.out.println(proportionVO);
        Admin admin = new Admin();
        admin.setAdminId(proportionVO.getId());
        admin.setProportion(proportionVO.getProportion());
        adminMapper.updateById(admin);
        return new Result(true,StatusCode.OK,"根据id修改提成比列成功！");
    }


    //根据id删除订单
    @RequestMapping("deleteOrderByID/{orderid}")
    public Result deleteOrderByID(@PathVariable Integer orderid){
        orderMapper.deleteById(orderid);
        return new Result(true,StatusCode.OK,"根据id删除订单成功！");
    }

    //查询全部订单
    @RequestMapping("findAllOrder/{pageindex}")
    public Result findAllOrder(@PathVariable Integer pageindex){
        QueryWrapper<OrderInfoVO> wrapper = new QueryWrapper<>();
//        wrapper.eq("##","##");
        Page<OrderInfoVO> page = new Page<>(pageindex, 5);
        Page<OrderInfoVO> OrderInfoVOPage = orderMapper.selectAllOrder(page,wrapper);
        return new Result(true,StatusCode.OK,"根据id修改提成比列成功！",OrderInfoVOPage);
    }

    //查询未支付订单
    @RequestMapping("findUnPayOrder/{pageindex}")
    public Result findUnPayOrder(@PathVariable Integer pageindex){
        QueryWrapper<OrderInfoVO> wrapper = new QueryWrapper<>();
        wrapper.eq("oin.order_status ","未支付");
        Page<OrderInfoVO> page = new Page<>(pageindex, 2);
        Page<OrderInfoVO> orderInfoList = orderMapper.findUnPayOrder(page,wrapper);
        return new Result(true,StatusCode.OK,"根据id修改提成比列成功！",orderInfoList);
    }

    //查询已支付订单
    @RequestMapping("findOnPayOrder/{pageindex}")
    public Result findOnPayOrder(@PathVariable Integer pageindex){
        QueryWrapper<OrderInfoVO> wrapper = new QueryWrapper<>();
        wrapper.eq("oin.order_status ","已支付");
        Page<OrderInfoVO> page = new Page<>(pageindex, 2);
        Page<OrderInfoVO> orderInfoList = orderMapper.findOnPayOrder(page,wrapper);
        return new Result(true,StatusCode.OK,"根据id修改提成比列成功！",orderInfoList);
    }

    //根据id查询订单
    @RequestMapping("findOrderByNumber/{ordernumber}")
    public Result findOrderByNumber(@PathVariable String ordernumber){
        List<OrderInfoVO> orderInfoList = orderMapper.deleteOrderByNumber(ordernumber);
        return new Result(true,StatusCode.OK,"根据id删除订单成功！",orderInfoList);
    }

    //获取首页数据
    @RequestMapping("getIndexTotal")
    public Result getIndexTotal(){
        HashMap<String, Integer> map = new HashMap<>();
        List<User> userList = userMapper.selectList(null);
        List<Parking> parkingList = parkingMapper.selectList(null);
        List<String> adminTotal = adminMapper.findAdminTotal();
        map.put("userTotal",userList.size());
        map.put("parkingTotal",parkingList.size());
        map.put("propertyTatol",adminTotal.size());
        return new Result(true,StatusCode.OK,"根据首页信息成功！",map);
    }


    //查询所有投诉
    @RequestMapping("findAllComplain/{pageindex}")
    public Result findAllComplain(@PathVariable Integer pageindex){
        Page<Complain> page = new Page<>(pageindex,5);
        Page<Complain> selectPage = complainMapper.selectPage(page, null);
        return new Result(true,StatusCode.OK,"根据首页信息成功！",selectPage);
    }

    //查询未处理投诉
    @RequestMapping("findUnComplian/{pageindex}")
    public Result findUnComplian(@PathVariable Integer pageindex){
        Page<Complain> page = new Page<>(pageindex,5);
        QueryWrapper<Complain> wrapper = new QueryWrapper<>();
        wrapper.eq("status","未处理");
        Page<Complain> selectPage = complainMapper.selectPage(page, wrapper);
        System.out.println(selectPage.getRecords());
        return new Result(true,StatusCode.OK,"根据首页信息成功！",selectPage);
    }

    //查询已处理投诉
    @RequestMapping("findOnComplian/{pageindex}")
    public Result findOnComplian(@PathVariable Integer pageindex){
        Page<Complain> page = new Page<>(pageindex,5);
        QueryWrapper<Complain> wrapper = new QueryWrapper<>();
        wrapper.eq("status","已处理");
        Page<Complain> selectPage = complainMapper.selectPage(page, wrapper);
        return new Result(true,StatusCode.OK,"根据首页信息成功！",selectPage);
    }

    // 处理投诉
    @RequestMapping("OnComplainByID/{id}")
    public Result OnComplainByID(@PathVariable Integer id){
        UpdateWrapper<Complain> wrapper = new UpdateWrapper<>();
        wrapper.eq("complainid",id);
        Complain complain = new Complain();
        complain.setStatus("已处理");
        complainMapper.update(complain,wrapper);
        return new Result(true,StatusCode.OK,"根据首页信息成功！");
    }


    @RequestMapping("deleteComplianByID/{id}")
    public Result deleteComplianByID(@PathVariable Integer id){
        complainMapper.deleteById(id);
        return new Result(true,StatusCode.OK,"根据首页信息成功！");
    }



}

