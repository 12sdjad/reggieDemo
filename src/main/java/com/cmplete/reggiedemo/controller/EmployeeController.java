package com.cmplete.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.entity.Employee;
import com.cmplete.reggiedemo.mapper.EmployeeMapper;
import com.cmplete.reggiedemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /*登入验证*/
    /*登入的逻辑*/
    /*
    * 根据id来查询数据库
    * 如果没有返回登入失败结果
    * 密码比对 不一致则返回
    * 查看员工状态 为1则正常 为0则是封号了
    * 登入成功 将id存session里面
    * */
    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){
        /*页面提交的密码进行加密处理*/
        String password=employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
//        下面这个方法不行是因为前端传的是userName不是id没法直接查
//        Employee byId1 = employeeService.getById(employee.getId());
        /*查询数据库中是否有这个账号*/
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee byId = employeeService.getOne(queryWrapper);
        log.info("登入的人:{}",byId);
        if (byId==null){
            return R.error("错误的账号");
        }
        if (!password.equals(byId.getPassword())){
            return R.error("错误的密码");
        }
        if (byId.getStatus()==0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",byId.getId());
        return R.success(byId);
    }
    /*如果要退出账号 则需要清空session*/
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("账号退出");
    }
    /*新建*/
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增{}",employee);
        /*设置初始密码*/
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        /*设置创建时间*/
//        employee.setCreateTime(LocalDateTime.now());
//        /*设置修改时间*/
//        employee.setUpdateTime(LocalDateTime.now());
        /*获得当前登入用户的id*/
    //    Long attribute = (Long)request.getSession().getAttribute("employee");
//        /*创建的人*/
//        employee.setCreateUser(attribute);
//        /*修改的人*/
//        employee.setUpdateUser(attribute);
        /*保存的方法*/
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
    /*分页查询*/
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        LambdaQueryWrapper<Employee> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);
        /*排序*/
        wrapper.orderByDesc(Employee::getUpdateTime);
        Page<Employee> pages=new Page<>(page,pageSize);
        employeeService.page(pages,wrapper);
        return R.success(pages);
    }
    /*根据id获取数据*/
    @GetMapping("/{id}")
    public R<Employee> selectById(@PathVariable Long id){
        log.info("获得的id:{}",id);
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
    @PutMapping()
    public R<String> upDate(@RequestBody Employee employee,HttpServletRequest request){
        log.info("需要修改的id的元素{}",employee);
        /*根据id来进行修改和改变状态*/
        /*根据公共字段来填充*/
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);
        return R.success("修改成功");
    }
}
