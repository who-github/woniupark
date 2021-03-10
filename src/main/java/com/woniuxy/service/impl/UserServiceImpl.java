package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.woniuxy.domain.User;
import com.woniuxy.domain.UserInfo;
import com.woniuxy.domain.UserVo;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import com.woniuxy.mapper.UserInfoMapper;
import com.woniuxy.mapper.UserMapper;
import com.woniuxy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.utils.JWTUtil;
import com.woniuxy.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 黄银发的代码：
     */
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public Result login(UserVo userVo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("tel",userVo.getUsername());
        User userDB = userMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(userDB)) {
            Md5Hash md5Hash = new Md5Hash(userVo.getPassword(), userDB.getSalt(), 2048);
            String md5Password = md5Hash.toHex();
            System.out.println(md5Password+"ssss");
            if(md5Password.equals(userDB.getPassword())){
                HashMap<String, String> map = new HashMap<>();
                map.put("tel",userVo.getUsername());
                String token = JWTUtil.createToken(map);
                return  new Result(true,200,"登陆成功",userDB);
            }else{
                return new Result(false, StatusCode.ERROR,"密码错误",null);
            }
        }else {
            return new Result(false,StatusCode.NULLPOINTERROR,"当前用户尚未注册",null);
        }
    }

    @Override
    public Result findByTel(UserVo userVo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("tel",userVo.getUsername());
        User userDB = userMapper.selectOne(wrapper);
        //判断用户是否存在
        if (ObjectUtils.isEmpty(userDB)) {
            String salt = SaltUtils.getSalt(5);
            System.out.println(salt+"加的盐");
            Md5Hash md5Hash = new Md5Hash(userVo.getPassword(),salt,2048);
            System.out.println(md5Hash+"盐");
            //保存加密后的密码
            User user = new User();
            user.setTel(userVo.getUsername());
            user.setSalt(salt);
            user.setPassword(md5Hash.toHex());
            //将user持久化到数据库
            userMapper.insert(user);
            Integer id = user.getId();
            System.out.println("===============================");
            UserInfo u1=new UserInfo();
            u1.setUserId(id);
            System.out.println(u1);
            userInfoMapper.insert(u1);
            return  new Result(true, StatusCode.OK,"注册成功",null);
        }else {
            return new Result(false,StatusCode.NULLPOINTERROR,"该用户已经被注册过",null);
        }
    }

    @Override
    public boolean findByUser(String tel) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("tel",tel);
        User user = userMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {

            return  true;
        }else {
            return false;
        }
    }

}
