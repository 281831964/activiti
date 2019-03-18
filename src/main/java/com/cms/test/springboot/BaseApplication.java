package com.cms.test.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cms.test.springboot.dao")
public class BaseApplication{
    public static void main( String[] args ){
        SpringApplication.run(BaseApplication.class, args);
    }
}
