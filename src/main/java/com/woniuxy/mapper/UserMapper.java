package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.domain.User;
import com.woniuxy.domain.UserVo;
import com.woniuxy.dto.Result;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 黄银发的代码：
     */
    //用户登录
    public Result login(UserVo userVo);
    //根据电话查询数据库是否存在
    public Result findByTel(UserVo userVo);
    //根据电话查询是否被注册
    public boolean findByUser(String tel);

}
