package rocketmq.demo.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConsumerOrderDemo {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer("order-group");
        mqPushConsumer.setNamesrvAddr("192.168.209.128:9876");
        mqPushConsumer.subscribe("order-topic","*");

        mqPushConsumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                System.out.println("queueId : " + context.getMessageQueue().getQueueId());
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {
                    System.out.println(Thread.currentThread().getName() + "msg : " + new String(msg.getBody(), StandardCharsets.UTF_8));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        mqPushConsumer.start();
    }
}
