package com.cxr.other.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class SyncProducer {
    public static void main(String[] args) throws Exception {
        sendMesAsyn();
    }

    public static void sendMesAsyn() throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = MqConfig.getProduct();
        for (int x = 0; x < 30; x++) {
            Message message = new Message("topicname", "sync_tags", ("消息1+" + x).getBytes());
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息：" + sendResult.getMsgId() + "发送成功");
                    SendStatus sendStatus = sendResult.getSendStatus();
                    MessageQueue messageQueue = sendResult.getMessageQueue();
                    String name = sendStatus.name();
                    String brokerName = messageQueue.getBrokerName();
                    int queueId = messageQueue.getQueueId();
                    System.out.println("sendStatusName：" + name + ",brokerName:" + brokerName + ",queueId:" + queueId);
                    System.out.println(JSON.toJSONString(sendResult));
                    System.out.println("====================");
                }

                @Override
                public void onException(Throwable throwable) {

                }
            });


        }
        producer.shutdown();
    }


}
