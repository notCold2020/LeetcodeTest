package com.cxr.other.schedules;

import com.cxr.other.point.AspectTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;

/**
 * https://www.pppet.net/changyong.html 在线
 * https://qqe2.com/cron  在线能看10次
 * https://www.cnblogs.com/yanghj010/p/10875151.html 解析
 */
@Component
public class SchedulesDemo {
    static Logger logger = LoggerFactory.getLogger(SchedulesDemo.class);

    /**
     * 上一个任务结束后 2s 执行下一次任务
     * 比如：任务本身要执行5s,那么就是每7s（2s任务+5s间隔）一次
     */
//    @Scheduled(fixedDelay = 2 * 1000l)
    void test01() {
        logger.info("--test031-");
    }


    /**
     * 距离上一次开始的时间 2s 执行下一次任务
     * 但是Springboot的定时任务默认是单线程的
     * 所以就会堆积，就像2s一到就把任务加入到任务队列了，但是前一个任务还没执行完，就只能等着。
     */
//    @Scheduled(fixedRate = 2 * 1000l)
    void test02() throws InterruptedException {
        logger.info("test02开始:");
        Thread.sleep(5000L);
        logger.info("test02结束:");
    }

    /**
     * 项目启动后 10s开始执行第一次定时任务，每2s执行一次
     */
//    @Scheduled(initialDelay = 10 * 1000, fixedDelay = 2 * 1000l)
    void test03() {
        logger.info("--test03--");
    }

    /**
     * @Schedule(cron = "秒 分 时 日 月 星期 [年]")
     * 1。设置成0表示整点执行，如果是*那就不一定整点执行了，可能是每隔10mins,任意秒
     * 2。
     */

//    */number表示“每隔...”，是最实用的
//    逗号表示“或”，比如 8,13,18 表示 8或13或18
    @Scheduled(cron = "0 5/30 7-22 * * ? ") //每天的7-22点，从05开始，每隔30mins在0s的时候执行一次。
    void test04() {

    }
}
