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
     * //发送事务消息
     * TransactionSendResult result = producer.sendMessageInTransaction(message, orderId);
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
     * LocalTransactionState.UNKNOW：未知，
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
