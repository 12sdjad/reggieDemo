package com.cmplete.reggiedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*开启注解*/
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieDemoApplication.class, args);
        log.info("项目启动成功");
    }

}
