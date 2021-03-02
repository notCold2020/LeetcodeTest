package com.cxr.other.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderProducer {

    public static void main(String[] args) throws Exception {
        //extends mqConfig
        DefaultMQProducer producer = new DefaultMQProducer("syncProducer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setRetryTimesWhenSendFailed(2);
        producer.start();

        for (int x = 0; x < 300; x++) {
            String orderId = x + "订单编号xxxxxx";
            Message message = new Message("sync_topic", "sync_tags", ("this is a order_sync--" + orderId).getBytes());

            SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
                System.out.println("queue selector mq nums:" + mqs.size());
                System.out.println("msg info： " + msg.toString());
                for (MessageQueue mq : mqs) {
                    System.out.println(mq.toString());
                }
                Integer id = (Integer) arg;//这一堆就是在配置messageQueue选择的规则
                int index = id % mqs.size();
                return mqs.get(index);
            }, orderId);

            System.out.println("###############################################");
            System.out.println("sendResult: " + sendResult);
        }

        //select是队列选择器
        String orderNum = "buySomething";
        SendResult send = producer.send(new Message(), new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                /**
                 * MessageQueue列表
                 * 要发送的消息
                 * 消息id 用于配置规则
                 */
                Long id = (Long) o;
                long index = id % list.size();
                return list.get((int) index);
            }
        }, orderNum);//传入了select 这个arg就是我们要发送的队列 也就是select方法的最后一个参数 在select方法里面我们配置选择的规则


        //人家写好的hash
        SendResult send1 = producer.send(new Message(), new SelectMessageQueueByHash(), 1000L);
        producer.shutdown();


    }
}
