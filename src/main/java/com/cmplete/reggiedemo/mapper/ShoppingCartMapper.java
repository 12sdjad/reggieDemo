package com.cmplete.reggiedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmplete.reggiedemo.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
