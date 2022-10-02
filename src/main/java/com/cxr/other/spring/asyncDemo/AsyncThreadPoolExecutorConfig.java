package com.cxr.other.spring.asyncDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
//@Configuration
public class AsyncThreadPoolExecutorConfig implements AsyncConfigurer {

    @Override
    /**
     * 如果重写了这个方法，@Async使用的线程池就会用下面这个
     */
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("AsyncThreadPoolExecutorConfig-@Async自定义线程池");

        // 线程池对拒绝任务的处理策略，拒绝策略应该自己配置，不然交给调用线程来处理直接给你服务器干碎
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @Override
    /**
     * 如果我们异步执行的方法出异常了，那么每次执行方法都会走到下面这里
     * 也就是AsyncUncaughtExceptionHandler接口中的handleUncaughtException方法
     *
     * 也可以new SimpleAsyncUncaughtExceptionHandler,这玩意都帮我们写好了，直接就会打印异常日志。
     */
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }
}

/**
 * 自定义异常处理类
 */
class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    /**
     * ctrl+option+b 看下下面的方法在哪被调用，发现其实就是在@Async那里，用一个try-catch捕获到了，
     * 下面的obj可变参数，其实就是我们方法的形参
     *
     * 在第二个catch捕获的
     */
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }
}