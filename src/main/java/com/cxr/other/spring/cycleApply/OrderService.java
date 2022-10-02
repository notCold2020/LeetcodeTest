package com.cxr.other.spring.cycleApply;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Date 2022/5/18 3:17 下午
 * @Created by CiXingrui
 */
//@Service
//@ComponentScan("com.cxr.other.spring.cycleApply")
@EnableAsync
public class OrderService {


    /**
     * OrderService中有@Async的方法，那么OrderService放进ioc容器的应该是个代理对象。
     * 
     * 0.order和user互相依赖
     * 1.order实例化 属性注入的时候 发现依赖userService
     * 2.UserService实例化 发现依赖orderService 但是三级缓存里面有order的元是引用 user实例化完成【还没放进ioc容器中】
     * 3.至此user引用的是order的原始引用
     * 4.order实例化完成，开始解析@async,吧自己解析成一个代理对象
     * 5.代理对象order放进ioc容器 user放进ioc容器
     * 6.报错了，因为user的引用和ioc容器中存在的【代理对象】不一致
     *
     * ====加上@Lazy
     * 这个结论就是，如果A类的一个属性上有@lazy，那么ioc容器里面的该对象也是cglib代理的。
     * 这样A引用的和容器里面的就一致了
     *
     */
    private UserService userService;

    @Async
    void asyncOrder(){

    }

}
