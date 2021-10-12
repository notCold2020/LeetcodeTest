package com.cxr.other.spring.singlePrototypeDemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@ComponentScan
public class UserDao {

    public UserDao(){
        System.out.println("UserDao 无参构造函数被调用");
    }
    //获取用户名
    public String getUserName(){
        //模拟dao层
        return "Alan_beijing";
    }
}
