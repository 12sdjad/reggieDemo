package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.entity.Employee;
import com.cmplete.reggiedemo.mapper.EmployeeMapper;
import com.cmplete.reggiedemo.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
