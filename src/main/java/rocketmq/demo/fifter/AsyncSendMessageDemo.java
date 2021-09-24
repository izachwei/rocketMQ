package rocketmq.demo.fifter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class AsyncSendMessageDemo {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer mqProducer = new DefaultMQProducer("test-group");

        mqProducer.setNamesrvAddr("192.168.209.128:9876");

        mqProducer.start();
        String msg = "第1条过滤消息";
        // String topic, String tags, String keys, int flag, byte[] body, boolean waitStoreMsgOK
        Message message = new Message("cmd-topic", msg.getBytes(StandardCharsets.UTF_8));
        message.putUserProperty("sex","女");
        message.putUserProperty("age","77");
        mqProducer.send(message);


        mqProducer.shutdown();
    }
}
