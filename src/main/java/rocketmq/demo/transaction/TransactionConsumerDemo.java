package rocketmq.demo.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class TransactionConsumerDemo {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer("transaction-group");
        mqPushConsumer.setNamesrvAddr("192.168.209.128:9876");

        mqPushConsumer.subscribe("transaction-topic", "*");

        mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("msg: " + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        mqPushConsumer.start();
    }
}
