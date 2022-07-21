package com.cmplete.reggiedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmplete.reggiedemo.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
