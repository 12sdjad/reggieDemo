package com.cmplete.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.dto.SetmealDto;
import com.cmplete.reggiedemo.entity.Category;
import com.cmplete.reggiedemo.entity.Setmeal;
import com.cmplete.reggiedemo.entity.SetmealDish;
import com.cmplete.reggiedemo.service.CategoryService;
import com.cmplete.reggiedemo.service.SetmealDishService;
import com.cmplete.reggiedemo.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService service;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        Page<SetmealDto> dtoPage=new Page<>();
        Page<Setmeal> setmealPage=new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        service.page(setmealPage);
        BeanUtils.copyProperties(setmealPage,dtoPage,"records");
        List<SetmealDto> collect = setmealPage.getRecords().stream().map(i -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(i, setmealDto);
            Long categoryId = i.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            if (categoryId!=null){
                String name1 = byId.getName();
                setmealDto.setCategoryName(name1);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(collect);
        return R.success(dtoPage);
    }
    /*保存方法*/
    @PostMapping()
    public R<String> save(@RequestBody SetmealDto setmealDto){

        log.info("套餐信息:{}",setmealDto);

        service.saveWithDish(setmealDto);

        return R.success("新增成功");
    }
    /*修改状态的代码*/
    @PostMapping("status/{status}")
    public R<String> upDate(@PathVariable("status") Integer status,@RequestParam List<Long> ids){
        log.info("id:{},status:{}",ids,status);
        List<Setmeal> setmeals = service.listByIds(ids);
        setmeals.forEach(i->{
            i.setStatus(status);
        });
        service.updateBatchById(setmeals);
        return R.success("状态修改成功");
    }
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        /*删除的套餐id 以下这样写也行但不咋好*/
//        for (int i=0;i<id.length;i++){
//            log.info("需要删除套餐的id{}",id[i]);
//            service.removeById(id[i]);
//            LambdaQueryWrapper<SetmealDish> wrapper=new LambdaQueryWrapper();
//            wrapper.eq(SetmealDish::getSetmealId,id[i]);
//            setmealDishService.remove(wrapper);
//        }
        service.removeWithDish(ids);
        return R.success("删除成功");
    }
    /*根据条件来查询套餐*/
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> wrapper=new LambdaQueryWrapper();
        wrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        wrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = service.list(wrapper);
        return R.success(list);
    }
}
