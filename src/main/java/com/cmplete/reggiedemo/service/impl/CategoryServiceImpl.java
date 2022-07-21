package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.common.CustomException;
import com.cmplete.reggiedemo.entity.Category;
import com.cmplete.reggiedemo.entity.Dish;
import com.cmplete.reggiedemo.entity.Setmeal;
import com.cmplete.reggiedemo.mapper.CategoryMapper;
import com.cmplete.reggiedemo.service.CategoryService;
import com.cmplete.reggiedemo.service.DishService;
import com.cmplete.reggiedemo.service.SetmealService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /*根据id删除分类，删除之前需要进行判断*/
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> wrapper=new LambdaQueryWrapper();
        //添加查询条件
        wrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(wrapper);
        /*查询当前分类是否关联了菜品，如果已经关联了，抛出一个异常*/
        if (count>0){
            //已经关联
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        /*查询当前分类是否关联了套餐，如果已经关联了，抛出一个异常*/
        LambdaQueryWrapper<Setmeal> wrapper1=new LambdaQueryWrapper<>();
        wrapper1.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(wrapper1);
        if (count1>0){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        /*正常删除分类*/
        super.removeById(id);
    }
}
