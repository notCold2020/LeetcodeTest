package com.cxr;

import com.cxr.other.spring.springListenerEvent.Test;
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
    public static void main(String[] args) throws InterruptedException {
        //这个run方法可以通过实现ApplicationRunner的方式重写
        SpringApplication.run(LeetcodetestApplication.class, args);
        System.out.println("===启动成功！===");
        Test bean = ApplicationUtils.getBean(Test.class);
        bean.testEvent();
    }
}
