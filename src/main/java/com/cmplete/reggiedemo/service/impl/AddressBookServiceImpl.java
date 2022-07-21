package com.cmplete.reggiedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmplete.reggiedemo.entity.AddressBook;
import com.cmplete.reggiedemo.mapper.AddressBookMapper;
import com.cmplete.reggiedemo.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
