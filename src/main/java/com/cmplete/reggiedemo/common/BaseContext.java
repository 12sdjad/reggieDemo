package com.cmplete.reggiedemo.common;
/*基于TreadLocal封装工具类，用户保存和获取当前登入用户的id*/
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void serCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
