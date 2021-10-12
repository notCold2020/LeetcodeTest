package com.cxr.other.spring.springListenerEvent;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义时间
 * 这个事件代表订单成功
 * ApplicationContextEvent extends
 */
public class OrderSuccessEvent extends ApplicationEvent {

    public OrderSuccessEvent(Object source) {
        super(source);
    }
}
