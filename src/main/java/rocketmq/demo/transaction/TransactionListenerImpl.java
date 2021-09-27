package rocketmq.demo.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionListenerImpl implements TransactionListener {

    public static final Map<String, LocalTransactionState> STATE_MAP = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            System.out.println("用户A账户减500元.");
            Thread.sleep(500); //模拟调用服务
             System.out.println(1/0);
            System.out.println("用户B账户加500元.");
            Thread.sleep(800);
            STATE_MAP.put(msg.getTransactionId(),
                    LocalTransactionState.COMMIT_MESSAGE);
            // 二次提交确认
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        STATE_MAP.put(msg.getTransactionId(),
                LocalTransactionState.ROLLBACK_MESSAGE);
        // 回滚
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        LocalTransactionState localTransactionState = STATE_MAP.get(msg.getTransactionId());
        return localTransactionState;
    }
}
