package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.entity.SetmealDish;
import com.cmplete.reggiedemo.mapper.SetmealDishMapper;
import com.cmplete.reggiedemo.mapper.SetmealMapper;
import com.cmplete.reggiedemo.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper,SetmealDish> implements SetmealDishService {
}
