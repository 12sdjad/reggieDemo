package com.cmplete.reggiedemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLClientInfoException;
import java.sql.SQLIntegrityConstraintViolationException;

/*全局异常捕获器*/
/*拦截有RestController注解*/
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalException {
    /*异常处理方法*/
    /*如果抛出了这个异常,则执行下面的方法*/
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> except(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")){
            /*截取出错误的名字*/
            String[] split=ex.getMessage().split(" ");
            String msg=split[2]+"已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public R<String> except1(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
