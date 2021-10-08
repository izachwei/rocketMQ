package rocketmq.demo.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConsumerDemo {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer("test-group");

        mqPushConsumer.setNamesrvAddr("192.168.209.128:9876");

        // 订阅主题
        mqPushConsumer.subscribe("cmd-topic", "*");

        // 消息监听用于 处理消息
        mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                msgs.forEach(msg -> System.out.println(new String(msg.getBody(), StandardCharsets.UTF_8)));
                System.out.println("msgs " + msgs);
                System.out.println("context " + context);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        mqPushConsumer.start();
    }
}
