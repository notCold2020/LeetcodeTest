package com.cxr.other.rocketmq.TransactionMQ;

import com.cxr.other.rocketmq.TransactionMQ.OrderTransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OrderService {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer();
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setProducerGroup("syncProducer");

        //自定义线程池,执行事务操作
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20), (Runnable r) -> new Thread("Order Transaction Massage Thread"));
        producer.setExecutorService(executor);

        //设置事务消息监听器
        producer.setTransactionListener(new OrderTransactionListener());
        producer.start();

        System.err.println("订单服务启动");

        for (int i = 0; i < 10; i++) {
            String orderId = UUID.randomUUID().toString() + "keys";
            String mes = "下单,orderId: " + orderId;
            String tags = "Tag";
            Message message = new Message("topicname", tags, orderId, mes.getBytes(RemotingHelper.DEFAULT_CHARSET));

            //发送事务消息
            TransactionSendResult result = producer.sendMessageInTransaction(message, orderId);
            System.err.println("发送事务消息,发送结果: " + result);
            System.out.println("================");
            Thread.sleep(1000L);
        }

        System.err.println("订单服务结束");
    }
}
