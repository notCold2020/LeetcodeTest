package com.cxr.other.spring.asyncDemo;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/25 5:34 下午
 */
@RestController
@EnableAsync
public class AsyncController {

    @Resource
    private AsyncService asyncService;

    /**
     * @Async生效2个条件
     * 1. 有@EnableAsync
     * 2. 因为@Async、@Transaction原理都是AOP，所以不能是普通的对象调用，必须得是代理对象调用增强之后的方法
     *
     * 结论：
     * @Async所在方法只能是返回值为void或者FutureTask，毕竟线程池要么就是没有返回值要么返回值是包装器
     *
     *
     * 1.@Async原理
     *  就是AOP,被调用的方法上面如果有@Async注解，那么方法所在的类在依赖注入的时候就是注入一个代理对象。这个代理对象会调用增强之后的方法，
     *  方法怎么增强的呢？就是把我们想要异步的方法，放到一个name叫applicationTaskExecutor的线程池中执行。这不就变成异步了吗
     *
     * 2.为什么阿里不推荐使用？
     * 默认的线程池最大线程数和最大队列数都是Integer.max 永远不会触发拒绝策略 都在内存里面
     * 所以我们使用@Async("xxx")指定自定义线程池
     *
     * 3.事务里面有@Async 事务还生效吗
     * 不生效，毕竟这是开了一个新的线程
     *
     * 4.@Async标注的方法能否读取ThreadLocal变量
     * 不能 新线程啊
     *
     */
    @RequestMapping("/testAsync")
    public void test() {
        System.out.println("AsyncController线程：" + Thread.currentThread().getName());
        asyncService.test01();
    }

}
