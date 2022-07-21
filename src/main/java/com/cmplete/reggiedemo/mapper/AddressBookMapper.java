package com.cmplete.reggiedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmplete.reggiedemo.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.Base64;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
