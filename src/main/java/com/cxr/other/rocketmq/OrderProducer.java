package com.cxr.other.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class OrderProducer {

    public static void main(String[] args) throws Exception {
        //extends mqConfig
        DefaultMQProducer producer = MqConfig.getProduct();

        for (int x = 0; x < 30; x++) {
            Integer orderId = x;
            Message message = new Message("topicname", "sync_tags", ("消息--" + orderId).getBytes());

            SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
                Integer id = (Integer) arg;//这一堆就是在配置messageQueue选择的规则
                int index = id % mqs.size();
                return mqs.get(index);
            }, orderId);

            System.out.println("发送成功：sendResult: " + JSON.toJSONString(sendResult));
            System.out.println("=================================");
        }


    }

    public void pushAsync() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = MqConfig.getProduct();

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
