package com.woniuxy.service.impl;

import com.woniuxy.domain.Order;
import com.woniuxy.mapper.OrderMapper;
import com.woniuxy.service.OrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
