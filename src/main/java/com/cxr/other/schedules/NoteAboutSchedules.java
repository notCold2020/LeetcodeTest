package com.cxr.other.schedules;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@EnableScheduling
public class NoteAboutSchedules {
    /**
     * 问题1：
     * 为什么在任何地方打上@EnableScheduling注解都可以让配置生效呢？
     * 这个注解做了什么？
     *
     * 这就是个后置处理器
     * 所有@Scheduled方法都已经被包装成Task存储到不同的任务列表中
     */
    void note1(){
        ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = new ScheduledAnnotationBeanPostProcessor();
//        scheduledAnnotationBeanPostProcessor.postProcessAfterInitialization();
    }

    /**
     * 1.明确概念：
     * 定时任务是单线程的 如果多个定时任务同时执行 就会单线程去排队
     * 我们的解决方法是用@Async 让其变为异步的 这个注解可以指定线程池
     *
     * 但这并不是最优解 简单理解就是@Schedules人家自己有一套多线程的方式，我们之前误打误撞用@Async让这个方法变成异步的（似乎是优先级更高）
     * 但是实际上用的还是原来的单线程 只不过是@Async注解生效了
     *
     * 突然想到：yml配置文件是不是可以更改所有的spring自带的配置，比如上面我们配置了@Schedules自己的多线程方式，我们就可以更改yml实现动态修改
     * 方式1：重写SchedulingConfigurer#configureTasks()
     * 方式2：@Bean + ThreadPoolTaskScheduler
     * 方式3：@Bean + ScheduledThreadPoolExecutor
     *
     */
}
