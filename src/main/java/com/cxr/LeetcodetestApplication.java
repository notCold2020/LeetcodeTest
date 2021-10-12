package com.cxr;

import com.cxr.other.demo.controller.UserController;
import com.cxr.other.utilsSelf.ApplicationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.cxr.other.tkMybatis")
//@EnableScheduling
//@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LeetcodetestApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeetcodetestApplication.class, args);
        System.out.println("===启动成功！===");
        UserController bean = (UserController)ApplicationUtils.getBean(UserController.class);
    }
}
