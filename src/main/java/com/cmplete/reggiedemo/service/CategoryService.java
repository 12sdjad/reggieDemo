package com.cmplete.reggiedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmplete.reggiedemo.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
