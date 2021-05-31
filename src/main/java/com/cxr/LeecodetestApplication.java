package com.cxr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.cxr.other.tkMybatis")
//@EnableScheduling
//@EnableAsync
public class LeecodetestApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeecodetestApplication.class, args);
    }
}
