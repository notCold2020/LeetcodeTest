package com.cxr.other.schedules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
/**
 * 一个关于线程池的坑：
 * test2模拟的是卡住的线程，可能是死锁。
 * 这样我们开启了3个线程，比如 1 2 3
 * 最初 1抢到了test1 2抢到了test2  3抢到了test3
 * 之后 1结束        2死锁呢      3结束
 * 下一轮定时任务开始
 * 就剩下2个线程 而这边同时唤醒了3个任务 又是随机 终究会耗尽线程的
 */
public class TestScedulesByThreadPool {

    @Scheduled(cron = "*/10 * * * * ?")
    @Async("taskExecutor_cixingrui_2")
    public void test1() {
        log.info("=========test1任务启动============");
        try {
            Thread.sleep(2 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("=========test1任务结束============");
    }

    @Scheduled(cron = "*/10 * * * * ?")
    @Async("taskExecutor_cixingrui_2")
    public void test2() {
        log.info("=========test2任务启动============");
        try {
            // 模拟远程服务卡死
            Thread.sleep(1000 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("=========test2任务结束============");
    }

    @Scheduled(cron = "*/10 * * * * ?")
    @Async("taskExecutor_cixingrui_2")
    public void test3() {
        log.info("=========test3任务启动============");
        try {
            Thread.sleep(2 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("=========test3任务结束============");
    }


    public static void main(String[] args) {
        //这不周期性执行定时任务的线程池吗
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        /**
         * 1) 待执行任务
         * 2）initialDelay 初次延时
         * 3）period 时间间隔
         */
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            //一些逻辑
        }, 2, 5, TimeUnit.SECONDS);

    }

}
