package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.dto.DishDto;
import com.cmplete.reggiedemo.entity.Category;
import com.cmplete.reggiedemo.entity.Dish;
import com.cmplete.reggiedemo.entity.DishFlavor;
import com.cmplete.reggiedemo.mapper.DishMapper;
import com.cmplete.reggiedemo.service.CategoryService;
import com.cmplete.reggiedemo.service.DishFlavorService;
import com.cmplete.reggiedemo.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        /*保存菜品基本信息到菜品表Dish*/
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        /*保存菜品口味数据到菜品口味表dish_flavor*/
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByWithFlavor(Long id) {
        Dish dishId = this.getById(id);
        Long id1 = dishId.getCategoryId();
        LambdaQueryWrapper<DishFlavor> wrapper=new LambdaQueryWrapper();
        wrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(wrapper);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dishId,dishDto);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    public void upDateWithFlavor(DishDto dishDto) {
        /*更新dish表基本信息*/
        this.updateById(dishDto);

        /*删除原先的口味表*/
       LambdaQueryWrapper<DishFlavor> wrapper=new LambdaQueryWrapper<>();
       wrapper.eq(DishFlavor::getDishId,dishDto.getId());
       dishFlavorService.remove(wrapper);
        /*更新口味表*/
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(i->{
            i.setDishId(dishDto.getId());
        });
//        flavors.stream().map(i->{
//            i.setDishId(dishDto.getId());
//            return i;
//        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

}
