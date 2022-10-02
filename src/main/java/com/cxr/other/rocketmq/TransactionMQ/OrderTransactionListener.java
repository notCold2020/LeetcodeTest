package com.cxr.other.rocketmq.TransactionMQ;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class OrderTransactionListener implements TransactionListener {
    private static final Map<String, Boolean> results = new ConcurrentHashMap<>();


    /**
     * //发送事务消息  A系统 -- MQ -- B系统
     * TransactionSendResult result = producer.sendMessageInTransaction(message, orderId);
     * 1.Product先发送个半消息给MQ，MQ收到之后回调下面的executeLocalTransaction，来执行Product的本地事务，根据executeLocalTransaction的返回值
     *   来判断是回滚还是提交这个半消息
     *      --提交：则说明A阶段消息落到MQ成功
     *      --回滚：说明A阶段本地事务失败了，就要把半消息删除掉
     *      --UNKNOW：这个状态其实是一个不确定的状态，RocketMQ在收到这个状态后，会定时多次进行反查，直到得到成功、失败的状态或者事务超时才结束。
     * 2.消息如果长时间在MQ那边处于半消息的状态，那么就会回调checkLocalTransaction判断这条消息的去/留
     *   这里可以在本地查询下数据库看看是不是下单成功
     *
     *
     * @param msg
     * @param arg  这个arg就是传进来的orderId
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String orderId = (String) arg;

        //记录本地事务执行结果
        boolean success = persistTransactionResult(orderId);
        System.err.println("订单服务执行本地事务下单,orderId: " + orderId + ", result: " + success);
        return success ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
    }

    /**
     * LocalTransactionState.UNKNOW：未知，RocketMQ在收到这个状态后，会定时多次进行反查，直到得到成功、失败的状态或者事务超时才结束。
     * 如果返回这个状态，这个消息既不提交，也不回滚，还是保持prepared状态，而最终决定这个消息命运的
     * 是checkLocalTransaction这个方法。
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String orderId = msg.getKeys();
        System.err.println("执行事务消息回查,orderId: " + orderId);
        return Boolean.TRUE.equals(results.get(orderId)) ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
    }

    //模拟本地下单操作 偶数下单成功
    private boolean persistTransactionResult(String orderId) {
        boolean success = Math.abs(Objects.hash(orderId)) % 2 == 0;
        results.put(orderId, success);
        return success;
    }
}
