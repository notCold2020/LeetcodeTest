package com.cxr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cxr.other.demo.dao")
public class LeecodetestApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeecodetestApplication.class, args);
    }
}
