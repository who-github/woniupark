package com.woniuxy.service.impl;

import com.woniuxy.domain.Admin;
import com.woniuxy.mapper.AdminMapper;
import com.woniuxy.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
