package com.cxr.other.schedules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

//@Configuration  //也得注入进ioc容器

@EnableScheduling //写了就行 不用非得写在启动类上
@EnableAsync
public class ThreadPoolTaskConfig implements WebMvcConfigurer {

    Logger log = LoggerFactory.getLogger(ThreadPoolTaskConfig.class);

    //错误案例
    @Bean("taskExecutor_cixingrui")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("async-task-");

        // 线程池对拒绝任务的处理策略，拒绝策略应该自己配置，不然交给调用线程来处理直接给你服务器干碎
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }


    /**
     * 分析一波流程：
     * 第一波定时任务：卡住1个线程
     * 第二波：又一个
     * 第三波：有一个  --》 核心core满了
     * 第四波：三个线程全都加入队列
     * 第五波：队列也满了 拒绝策略
     */
    @Bean("taskExecutor_cixingrui_2")
    public Executor taskExecutorBetter() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("async-task-");

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            /**
             * 自定义线程池拒绝策略（模拟发送告警邮件）
             */
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.info("发送告警邮件======>:嘿沙雕，线上定时任务卡爆了, 当前线程名称为:{}, 当前线程池队列长度为:{}",
                        r.toString(),
                        executor.getQueue().size());
            }
        });
        // 初始化
        executor.initialize();
        return executor;
    }


    /**
     * WebMvcConfigurer 就是个配置的接口，里面有好多用default修饰的方法，我们自己来实现
     * <p>
     * 比如下面这个，就是我们自定义配置的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return false;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        }).addPathPatterns("/**");
    }


}
