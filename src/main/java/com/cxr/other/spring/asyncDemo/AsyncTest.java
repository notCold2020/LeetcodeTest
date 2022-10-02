package com.cxr.other.spring.asyncDemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/25 5:34 下午
 */
@EnableAsync
public class AsyncTest {

    /**
     * @Async生效3个条件
     * 1. 有@EnableAsync
     * 2. 因为是代理对象，所以必须是public方法
     * 3. 并且是代理类调用目标方法 才能走增强的逻辑
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
     * 而且这个线程池SimpleAsyncTaskExecutor	每次请求新开线程，没有最大线程数设置.不是真的线程池，这个类不重用线程，每次调用都会创建一个新的线程。
     * 所以我们使用@Async("xxx")指定自定义线程池
     *
     * 3.事务里面有@Async 事务还生效吗
     * 不生效，毕竟这是开了一个新的线程.
     * 事务用ThreadLocal存储数据库connect，新开了一个线程就拿不到连接了。
     *
     * 4.@Async标注的方法能否读取ThreadLocal变量
     * 不能 新线程啊(这里的新线程是指当前的请求过来的线程，创建了一个新线程，在主线程中去获取肯定是获取不到)
     *
     * 5.@Async所在方法的返回值只能是FutrueTask或者void，为什么
     * 再怎么变，提交任务无非就是execute()和submit()  这俩返回值一个是Future<>一个是void
     *
     *
     * 6.A类里面有方法1，2   1方法是普通方法，2方法有@Async，这个时候A类是代理对象，但是代理对象A调用1方法的，1方法内部调用有@Async的2方法，这个时候
     * 异步方法失效，为什么？
     * 代理对象调用方法的时候会进行判断，即一个match()，是不是当前这个方法是public || 方法上有@Async注解，这个例子上是方法上没@async，就像当与
     * 直接是一个普通的方法调用，自然没办法走增强过的代理逻辑
     *
     *
     * 7.还是上面的例子，很自然会想到在A类里面注入自己，在1方法里面通过注入的自己来调用2方法，发生了循环依赖，为什么？
     * 项目启动
     * 1.A实例化，把自己标记成创建过程中，发现依赖A,实例化A
     * 2.因为A在创建过程中，从三级缓存中获取到了原始的A(反射newInstance创建)，至此A类里面注入的是原始对象普通的A【注意这里从三级缓存中获取到的是个普通对象，而不是代理对象】
     * 3.然后A类依赖注入完毕，进行初始化，在后置处理器获得一个代理对象【后置处理器中获得代理对象】，放入ioc容器
     * 4.容器自检，发现容器里面挨说的是代理对象，但是A类使用的是原始对象，报错
     *
     *
     * 8.为什么上面的例子，依赖注入的全局变量上面加@Lazy就解决了循环依赖呢？
     * 1.A实例化，把自己标记成创建过程中，发现依赖A,实例化A
     * 2.依赖注入的时候发现有@lazy,直接注入一个代理对象
     * 3.然后A类依赖注入完毕，进行初始化，在后置处理器获得一个代理对象，放入i
     * 4.容器自检，发现容器里面的是代理对象，A类使用的也是代理对象，👌
     *
     *
     * 9.@Async，@Transaction 什么时候失效
     * 直接被调用的方法是private或者没有相应注解
     *
     *
     * 10.为什么@Transactional可以注入自身
     * 虽说他俩的原理都是产生代理对象，且注解的使用方式几乎无异。so区别Spring对它哥俩的解析不同，也就是他们代理的创建的方式不同：
     *
     * @Transactional使用的是自动代理创建器AbstractAutoProxyCreator，它实现了getEarlyBeanReference()方法从而很好的对循环依赖提供了支持
     * 从三级缓存中拿到的就是代理对象，自然可以解决循环依赖
     *
     * @Async的代理创建使用的是AsyncAnnotationBeanPostProcessor单独的后置处理器实现的，它只在一处postProcessAfterInitialization()实现了对代理对象的创建
     * 就是初始化后的后置处理器
     *
     *
     *
     */

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AsyncConfig.class);
        AsyncService bean = applicationContext.getBean(AsyncService.class);
        bean.test02();
        System.out.println("🆗");
        Thread.sleep(10000L);
    }

}
