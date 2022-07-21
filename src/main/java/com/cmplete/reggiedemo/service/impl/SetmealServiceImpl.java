package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.common.CustomException;
import com.cmplete.reggiedemo.dto.SetmealDto;
import com.cmplete.reggiedemo.entity.Category;
import com.cmplete.reggiedemo.entity.Setmeal;
import com.cmplete.reggiedemo.entity.SetmealDish;
import com.cmplete.reggiedemo.mapper.SetmealMapper;
import com.cmplete.reggiedemo.service.CategoryService;
import com.cmplete.reggiedemo.service.SetmealDishService;
import com.cmplete.reggiedemo.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        /*保存关联关系*/
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> collect = setmealDishes.stream().map(i -> {
            i.setSetmealId(setmealDto.getId());
            return i;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(collect);
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态,曲儿是否可以删除
        LambdaQueryWrapper<Setmeal> wrapper=new LambdaQueryWrapper();
        wrapper.in(Setmeal::getId,ids);
        wrapper.eq(Setmeal::getStatus,1);
        int i = this.count(wrapper);
        if (i>0){
            throw new CustomException("套餐正在售卖中");
        }
        //如果可以,先删除表中数据
        this.removeByIds(ids);
        //删除关系数据
        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper);
    }
}
