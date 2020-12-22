package com.cxr.other.juc;

import com.cxr.other.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 正常来讲应该 ok 不会停
 * 反正本质上就是B线程修改了工作内存的值并且写回主内存 但是A线程不知道 A的工作内存依旧
 */
public class JMMDemo {
    private static int num = 0;
    static Logger logger = LoggerFactory.getLogger(JMMDemo.class);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (num == 0) {
                System.out.println("OK");
            }
        },"A").start();
        //确保A线程读到了num=0 到自己的工作内存中
        TimeUnit.SECONDS.sleep(2);
        num = 1;
        logger.info("我变1了");
    }
}
