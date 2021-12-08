package com.cxr.other.strangeDemo.queue;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.DelayQueue;

/**
 * @Author: CiXingrui
 * @Create: 2021/11/14 1:42 下午
 */
public class DelayOrderService {
    /**
     * 订单信息
     */
    private Object order;

    /**
     * 过期时间
     */
    private Long time;

    /**
     * 存储时间的队列
     */
    static DelayQueue<DelayObject<Object>> queue = new DelayQueue<>();

    public DelayOrderService(Object order, Long time) {
        this.order = order;
        this.time = time;
    }

    public void addOrder(Object order, Long time) {
        DelayObject<Object> objectDelayObject = new DelayObject<>(order, time);
        queue.put(objectDelayObject);
    }


    Thread thread;

    @PostConstruct
    /**
     * 项目一启动的时候就把这个线程跑起来
     */
    public void init() {
        thread = new Thread(new TakeOrder());
        thread.start();
    }

    @PreDestroy
    public void close() {
        thread.interrupt();
    }


    /**
     * 场景：我们希望订单下单之后，如果规定时间内没有付款，那么就取消订单/催一下
     */
    static class TakeOrder implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                DelayObject<Object> take = queue.take();
                //拿到就说明订单已经过期(到达我们设定的过期时间)

                /**
                 * 去数据库检查订单的状态
                 * 1.如果已经支付 就ok
                 * 2.如果没有支付 就把订单状态改为过期
                 */

            }
        }
    }
}
