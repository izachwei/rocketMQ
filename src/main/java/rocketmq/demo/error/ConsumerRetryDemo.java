package rocketmq.demo.error;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class ConsumerRetryDemo {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();

        defaultMQPushConsumer.setNamesrvAddr("192.168.209.128:9876");
        defaultMQPushConsumer.subscribe("retry-topic","*");
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                System.out.println("msg : " + new String(msg.getBody()));
                if (msg.getReconsumeTimes() >= 3){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return null;
        });
        defaultMQPushConsumer.start();
//        DefaultLitePullConsumer defaultLitePullConsumer = new DefaultLitePullConsumer("retry-group");
//
//        defaultLitePullConsumer.setNamesrvAddr("192.168.209.128:9876");
//        defaultLitePullConsumer.poll();
    }
}
