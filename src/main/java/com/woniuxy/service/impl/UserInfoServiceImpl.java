package com.woniuxy.service.impl;

import com.woniuxy.domain.UserInfo;
import com.woniuxy.mapper.UserInfoMapper;
import com.woniuxy.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
