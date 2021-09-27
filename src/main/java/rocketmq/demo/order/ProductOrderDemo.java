package rocketmq.demo.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class ProductOrderDemo {

    /**
     * 顺序消息 发送流程
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer mqProducer = new DefaultMQProducer("order-group");
        mqProducer.setNamesrvAddr("192.168.209.128:9876");

        mqProducer.start();

        // 发送消息
        for (int i = 0; i < 100; i++) {
            Integer orderId = i % 10;
            String msg = "第" + i + "条顺序消息,orderId: " + orderId;
            Message message = new Message("order-topic", "order", msg.getBytes(StandardCharsets.UTF_8));
            mqProducer.send(message, (mqs, msg1, arg) -> {
                int value = arg.hashCode() % mqs.size();
                if (value < 0) {
                    value = Math.abs(value);
                }
                return mqs.get(value);
            }, orderId);
        }

        mqProducer.shutdown();

    }
}
