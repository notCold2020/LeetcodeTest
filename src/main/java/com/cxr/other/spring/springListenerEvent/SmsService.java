package com.cxr.other.spring.springListenerEvent;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 短信服务，监听OrderSuccessEvent，但不用实现ApplicationListener
 *
 * OrderSuccessEvent事件一旦触发，就会广播OrderSuccessEvent事件，就被sendSms捕获到了。
 */
@Service
public class SmsService {

    /**
     * 发送短信 @EventListener指定监听的事件
     */
//    @EventListener(OrderSuccessEvent.class)
//    public void sendSms() {
//
//        try {
//            Thread.sleep(1000L * 5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("监听到了事件，即将发送短信...");
//    }

    /**
     * 如果不指定 可以直接通过这个参数来找到对应的listener
     * 当然下面的方式也可以
     *
     * 那么如果有三个方法，AB的入参一样，C的注解里面指定的都一样会怎么样呢？
     * 先执行@EventListener指定的方法也就是下面的这个
     * 然后下面的两个方法都会执行
     * ================================================================
     * 下面这个@Order 注意是spring.core包下面的
     * 如果 设置了异步 那么@order就失效了
     * 我猜测应该是线程池无法指定执行顺序
     */
    @EventListener(EventDTO.class)
    @Order(2)
    public void eventDTOTest() {

        try {
            Thread.sleep(1000L * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("监听到了事件，即将发送短信3...");
    }

    @EventListener
    @Order(0)
    public void eventDTOTest(EventDTO eventDTO) {

        try {
            Thread.sleep(1000L * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("监听到了事件，即将发送短信1...");
    }

    @EventListener
    @Order(1)
    public void eventDTOTest2(EventDTO eventDTO) {

        try {
            Thread.sleep(1000L * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("监听到了事件，即将发送短信2...");
    }


}
