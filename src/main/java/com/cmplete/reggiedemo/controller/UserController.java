package com.cmplete.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.entity.User;
import com.cmplete.reggiedemo.service.UserService;
import com.cmplete.reggiedemo.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    /*发送手机验证码*/
    @PostMapping("/login")
    public R<User> login(@RequestBody User user, HttpSession session){
        /*获取手机号*/
        String phone = user.getPhone();
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper();
        wrapper.eq(User::getPhone,phone);
        User one = userService.getOne(wrapper);
        if (one == null){
            one=new User();
            one.setStatus(1);
            one.setPhone(phone);
            userService.save(one);
        }
        session.setAttribute("user",user);
        return  R.success(one);
    }
}
