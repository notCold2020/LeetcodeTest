package com.cxr.other.spring.springListenerEvent;

import org.springframework.context.event.EventListener;
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
    @EventListener(OrderSuccessEvent.class)
    public void sendSms() {

        try {
            Thread.sleep(1000L * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("监听到了事件，即将发送短信...");
    }

}
