package com.cxr.other.spring.transaction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Date 2022/6/2 8:09 下午
 * @Created by CiXingrui
 */
@PropertySource(value = "classpath:application.yml")
@MapperScan("com.cxr.other.spring.transaction")
@SpringBootApplication
public class transactioncConfig {

}
