package rocketmq.demo.error;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class ProductRetryMessageDemo {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer mqProducer = new DefaultMQProducer("retry-group");
        mqProducer.setNamesrvAddr("192.168.209.128:9876");
        // 设置同步发送消息失败重试次数
        mqProducer.setRetryTimesWhenSendFailed(3);
        // 设置异步发送消息失败重试次数
        mqProducer.setRetryTimesWhenSendAsyncFailed(3);

        mqProducer.start();
        String msgStr = "重试消息";
        Message msg = new Message("retry-topic","pro",msgStr.getBytes(StandardCharsets.UTF_8));

        // 设置消息超时
        mqProducer.send(msg,3000);
    }
}
