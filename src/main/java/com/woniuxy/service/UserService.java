package com.woniuxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.User;
import com.woniuxy.domain.UserVo;
import com.woniuxy.dto.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface UserService extends IService<User> {

    /**
     * 黄银发的代码：
     */
    //用户登录
    Result login(UserVo userVo);
    //根据电话查询数据库是否存在
    Result findByTel(UserVo userVo);
    //根据电话查询是否被注册
    boolean findByUser(String tel);

}
