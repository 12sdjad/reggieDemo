package com.cmplete.reggiedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmplete.reggiedemo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.MatrixVariable;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
