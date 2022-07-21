package com.cmplete.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.dto.DishDto;
import com.cmplete.reggiedemo.entity.Category;
import com.cmplete.reggiedemo.entity.Dish;
import com.cmplete.reggiedemo.entity.DishFlavor;
import com.cmplete.reggiedemo.service.CategoryService;
import com.cmplete.reggiedemo.service.DishFlavorService;
import com.cmplete.reggiedemo.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
/*菜品管理*/
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        LambdaQueryWrapper<Dish> wrapper=new LambdaQueryWrapper();
        wrapper.orderByDesc(Dish::getSort);
        wrapper.like(name!=null,Dish::getName,name);
        Page<Dish> pages=new Page<>(page,pageSize);
        dishService.page(pages,wrapper);
        Page<DishDto> dishDtoPage=new Page<>();
        /*对象拷贝*/
        BeanUtils.copyProperties(pages,dishDtoPage,"records");

        List<Dish> dishes = pages.getRecords();
        List<DishDto> list=dishes.stream().map((item)->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long id = item.getCategoryId();
            //根据id 查询分类对象
            Category id1 = categoryService.getById(id);
            if (id1!=null){
                String name1 = id1.getName();
                dishDto.setCategoryName(name1);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }
    /*新增菜品*/
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("菜品数据为:{}",dishDto);
        /*前端传的数据,需要两张表来进行保存*/
//        dishService.save(dishDto);
//        dishFlavorService.saveBatch(dishDto.getFlavors());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增成功");
    }
    /*根据id获取菜品*/
    @GetMapping("/{id}")
    public R<DishDto> selectById(@PathVariable Long id){
        log.info("获取的id:{}",id);
        DishDto dishDto = dishService.getByWithFlavor(id);
        Long categoryId = dishDto.getCategoryId();
        Category category = categoryService.getById(categoryId);
        String name = category.getName();
        dishDto.setCategoryName(name);
        return R.success(dishDto);
    }
    @PutMapping
    /*更新操作*/
    public R<String> upDate(@RequestBody DishDto dishDto){
        log.info("更新的数据是{}",dishDto);
//        dishService.save(dishDto);
//        dishFlavorService.saveBatch(dishDto.getFlavors());
        dishService.upDateWithFlavor(dishDto);
        return R.success("更新成功");
    }
    /*根据条件来查询菜品数据*/
    @GetMapping("/list")
    public R<List<DishDto>> listR(Dish dish){
        LambdaQueryWrapper<Dish> wrapper=new LambdaQueryWrapper();

        wrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        /*查询状态是1的*/
        wrapper.eq(Dish::getStatus,1);
        wrapper.orderByAsc(Dish::getSort);
        List<Dish> list = dishService.list(wrapper);
        List<DishDto> dishDtoList=list.stream().map(i->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(i,dishDto);
            Long id = i.getCategoryId();
            Category byId = categoryService.getById(id);
            if (byId!=null){
                String name = byId.getName();
                dishDto.setCategoryName(name);
            }
            Long iId = i.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,iId);
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtoList);
    }
}
