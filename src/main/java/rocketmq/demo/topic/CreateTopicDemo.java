package rocketmq.demo.topic;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class CreateTopicDemo {

    public static void main(String[] args) {
        // 创建生产者
        DefaultMQProducer mqProducer = new DefaultMQProducer();

        // nameserver Address
        mqProducer.setNamesrvAddr("192.168.209.128:9876");


        // d
        // 创建Topic
//        mqProducer.createTopic();
    }
}
