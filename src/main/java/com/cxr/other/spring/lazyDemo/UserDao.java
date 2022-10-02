package com.cxr.other.spring.lazyDemo;

//@ComponentScan
//@Service
//@Lazy
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
