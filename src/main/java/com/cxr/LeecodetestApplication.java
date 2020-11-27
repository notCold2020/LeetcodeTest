package com.cxr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.cxr.other.tkMybatis")
public class LeecodetestApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeecodetestApplication.class, args);
    }
}
