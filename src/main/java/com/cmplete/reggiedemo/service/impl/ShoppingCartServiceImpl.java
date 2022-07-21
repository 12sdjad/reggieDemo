package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.entity.ShoppingCart;
import com.cmplete.reggiedemo.mapper.ShoppingCartMapper;
import com.cmplete.reggiedemo.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
