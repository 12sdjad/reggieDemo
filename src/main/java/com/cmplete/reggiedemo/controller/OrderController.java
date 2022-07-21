package com.cmplete.reggiedemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.entity.Orders;
import com.cmplete.reggiedemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }
    @GetMapping("page")
    public R<Page> pageR(int page,int pageSize){
        return null;
    }
}
