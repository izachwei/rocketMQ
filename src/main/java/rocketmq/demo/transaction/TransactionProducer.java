package rocketmq.demo.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TransactionProducer {

    public static void main(String[] args) throws UnsupportedEncodingException, MQClientException {
        TransactionMQProducer mqProducer = new TransactionMQProducer("transaction-group");
        mqProducer.setNamesrvAddr("192.168.209.128:9876");
        // 设置事务监听器
        mqProducer.setTransactionListener(new TransactionListenerImpl());
        mqProducer.start();
        // 发送消息
        Message message = new Message("transaction-topic", "用户A给用户B转账500 元".getBytes(StandardCharsets.UTF_8));
        mqProducer.sendMessageInTransaction(message,null);

    }
}
