package com.cmplete.reggiedemo.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*自定义数据对象处理*/
@Component
@Slf4j
public class MyMetaObjeccthandle implements MetaObjectHandler {
    /*插入操作自动填充*/
    @Override
    public void insertFill(MetaObject metaObject) {
        long id = Thread.currentThread().getId();
        log.info("线程id：{}",id);
        log.info("公共字段自动填充[insert]");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
    /*更新操作自动填充*/
    @Override
    public void updateFill(MetaObject metaObject) {
        long id = Thread.currentThread().getId();
        log.info("线程id：{}",id);
        log.info("公共字段自动填充[upDate]");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
