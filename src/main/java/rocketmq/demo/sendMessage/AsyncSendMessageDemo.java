package rocketmq.demo.sendMessage;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class AsyncSendMessageDemo {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer mqProducer = new DefaultMQProducer("test-group");

        mqProducer.setNamesrvAddr("192.168.209.128:9876");

        mqProducer.start();

        for (int i = 0; i < 100; i++) {
            String msg = "第" + i + "条消息";
            // String topic, String tags, String keys, int flag, byte[] body, boolean waitStoreMsgOK
            Message message = new Message("cmd-topic", msg.getBytes(StandardCharsets.UTF_8));

            mqProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息发送成功," + sendResult.toString());
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                    System.out.println("消息发送失败，错误信息：" + e.getMessage());
                }
            });
        }

//        mqProducer.shutdown();
    }
}
