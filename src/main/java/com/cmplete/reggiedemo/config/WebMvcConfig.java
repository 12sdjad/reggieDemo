package com.cmplete.reggiedemo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.cmplete.reggiedemo.common.JacksonObjectMapper;
import com.cmplete.reggiedemo.filter.LoginCheckFilter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*设置静态资源映射*/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        String[] uris=new String[]{
//                "/employee/login",
//                "/employee/logout",
//                "/backend/page/login/login.html",
//                "/front/**"
//        };
//        registry.addInterceptor(new LoginCheckFilter()).addPathPatterns("/**").excludePathPatterns(uris);
//    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
    /*扩展mvc消息转换器 有瑕疵和前端对*/
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*创建消息转换器*/
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
        /*设置对象转换器,底层使用Jackson将java对象转未json*/
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        /*将消息转换器对象追加到mvc框架中*/
        converters.add(0,messageConverter);
    }
}
