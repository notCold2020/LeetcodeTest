package com.cxr.other.spring.aspect;

import com.cxr.other.demo.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AspectTest{
    /**
     * 这个实际上是代理对象来进行操作 所以如果我们想实现一个请求 如果请求中携带某个参数 我们就让这个请求return掉 before()方法是不行的
     * 因为实际上是代理对象来给我们做的操作 我们return只是把代理对象return了
     * 所以用拦截器/过滤器
     * 过滤器里面的方法只进不出 init()[项目一点启动就执行这个] doFilter()   destroy()
     * 拦截器有来有回  preHandle()   postHandle()   afterCompletion()
     *
     */
    static Logger logger = LoggerFactory.getLogger(AspectTest.class);

    /**
     * @see UserController#aspectTest()
     */
    public String beforeTest() {
        System.out.println("--");
        return "123";
    }
}
