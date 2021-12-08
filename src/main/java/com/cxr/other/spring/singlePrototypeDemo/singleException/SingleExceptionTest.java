package com.cxr.other.spring.singlePrototypeDemo.singleException;

import com.cxr.other.spring.singlePrototypeDemo.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/12 10:52 上午
 */
@Component
public class SingleExceptionTest {


    public static void main(String[] args) throws InterruptedException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserDao.class);

        /**
         * 这种多线程环境操作单例模式的全局变量，就会有问题。
         * https://xiejun.blog.csdn.net/article/details/105738223
         */
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                SingleExceptionDemo bean = applicationContext.getBean(SingleExceptionDemo.class);
                bean.test();
            }).start();
        }
        Thread.sleep(3000L);
        SingleExceptionDemo bean = applicationContext.getBean(SingleExceptionDemo.class);
        int i1 = bean.getI();
        System.out.println(i1+"--");
    }
}
