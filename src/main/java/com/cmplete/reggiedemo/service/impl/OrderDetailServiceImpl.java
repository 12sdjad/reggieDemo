package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.entity.OrderDetail;
import com.cmplete.reggiedemo.mapper.OrderDetailMapper;
import com.cmplete.reggiedemo.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
