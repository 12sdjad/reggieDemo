package com.cmplete.reggiedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmplete.reggiedemo.dto.DishDto;
import com.cmplete.reggiedemo.entity.Dish;
import com.cmplete.reggiedemo.entity.DishFlavor;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品,同时插入菜品对应的口味数据,需要操作两张表dish,dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByWithFlavor(Long id);

    void upDateWithFlavor(DishDto dishDto);
}
