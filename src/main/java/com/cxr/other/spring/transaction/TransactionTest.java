package com.cxr.other.spring.transaction;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/15 11:00 上午
 */

@Component
public class TransactionTest {

    @Autowired
    private TransactionDemo transactionDemo;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(transactioncConfig.class);
        TransactionDemo bean = applicationContext.getBean(TransactionDemo.class);
        try {
            for (int i = 5000001; i < 10000000; i++) {
                bean.test(i);
            }
        } catch (Exception e) {
            System.out.println("@@" + JSON.toJSONString(e));
        }
        System.out.println("alalalla");
    }
}
