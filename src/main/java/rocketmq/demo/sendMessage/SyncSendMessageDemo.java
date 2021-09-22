package rocketmq.demo.sendMessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class SyncSendMessageDemo {

    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer mqProducer = new DefaultMQProducer("test-group");

        // 设置nameserver地址
        mqProducer.setNamesrvAddr("192.168.209.128:9876");

        mqProducer.start();
        for (int i = 0; i <100 ; i++) {
            String msg = "我的第一条消息"+1;

            Message message = new Message("my-topic2","my-tag","key1",msg.getBytes("UTF-8"));

            SendResult send = mqProducer.send(message);
            System.out.println(send);

        }




        mqProducer.shutdown();

    }
}
