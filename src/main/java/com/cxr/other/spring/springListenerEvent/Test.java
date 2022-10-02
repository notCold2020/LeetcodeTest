package com.cxr.other.spring.springListenerEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Test {

    /**
     * 这个applicationContext在项目启动之后就存在了，咱们直接拿出来就行。
     * 我理解setApplicationContext()就是Aware接口那里是为了把这个applicationContext放到我们自己写的工具类里面
     */
    @Autowired
    private  ApplicationContext applicationContext;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void testEvent() throws InterruptedException {
        /**
         * 同步事件:广播事件，然后被打了@EventListener(OrderSuccessEvent.class)的方法捕获到，开始执行这个方法（比如耗时3s）,
         * 等这三秒执行完了才继续往下走
         *
         * 异步事件，配置了applicationEventMulticaster.setTaskExecutor()线程默认就变成异步了，和MQ就一样了，事件发出去了就当做执行完了，上面的
         * 3s咱们也不用等了。
         * 或者最简单粗暴就是个new Thread().start()也行
         */
//        applicationContext.publishEvent(new OrderSuccessEvent(this));//this是事件源，
        applicationEventPublisher.publishEvent(new EventDTO());
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 下面的testEvent方法debug进去，发现订阅的方法(就是要执行的方法)已经在一个叫method的全局变量中了
         * 如果是在被调用方法上打注解 就反射调用
         * 如果是实现接口的方式，就直接调用
         *
         * 这一套的注册 初始化 是在refresh刷新之后监听一个applicationContext事件
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ListenerConfig.class);
        Test bean = applicationContext.getBean(Test.class);
        bean.testEvent();
    }

}
