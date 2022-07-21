package com.cmplete.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.entity.Category;
import com.cmplete.reggiedemo.service.CategoryService;
import com.cmplete.reggiedemo.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* 分类管理
* */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;
    /*新增方法*/
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("获取的数据是:{}",category);
        categoryService.save(category);
        return R.success("新增成功");
    }
    /*菜品分页*/
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> p=new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(p,wrapper);
        return R.success(p);
    }
    /*分类删除*/
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
        log.info("删除分类,id为{}",id);
        categoryService.remove(id);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类的信息{}",category);
        categoryService.updateById(category);
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Category>> select(Category category){
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper();
        wrapper.eq(category.getType()!=null,Category::getType,category.getType());
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }
}
