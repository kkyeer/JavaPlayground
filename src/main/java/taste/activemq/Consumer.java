package taste.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: kkyeer
 * @Description: 消费者
 * @Date:Created in 17:04 2019/8/29
 * @Modified By:
 */
class Consumer {
    private final ConnectionFactory CONNECTION_FACTORY;
    private final Connection CONNECTION;
    private final Session SESSION;
    private ThreadLocal<MessageConsumer> messageConsumerThreadLocal = new ThreadLocal<>();
    private static final ConcurrentHashMap<String, Queue> QUEUE_MAP = new ConcurrentHashMap<>();

    Consumer() throws JMSException {
        this.CONNECTION_FACTORY = new ActiveMQConnectionFactory(ActiveMqConfig.USERNAME, ActiveMqConfig.PASSWORD, ActiveMqConfig.BROKER_URL);
        this.CONNECTION = this.CONNECTION_FACTORY.createConnection();
        this.CONNECTION.start();
        this.SESSION = this.CONNECTION.createSession(true, Session.SESSION_TRANSACTED);
    }

    void getMessage(String queueName) {
        MessageConsumer messageConsumer = messageConsumerThreadLocal.get();
        if (messageConsumer == null) {
            System.out.println("Consumer is null");
            try {
                Queue queue = QUEUE_MAP.computeIfAbsent(queueName,(queueNameString)-> {
                    try {
                        System.out.println(Thread.currentThread().getName()+" is Creating new Queue:"+queueName);
                        return SESSION.createQueue(queueNameString);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
                messageConsumer = SESSION.createConsumer(queue);
                System.out.println(Thread.currentThread().getName()+" created new consumer:"+messageConsumer);
                messageConsumerThreadLocal.set(messageConsumer);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                TextMessage message = (TextMessage) messageConsumer.receive();
                if (message != null) {
                    message.acknowledge();
                    System.out.println(Thread.currentThread().getName() + " received: " + message.getText());
                }
                Thread.yield();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws JMSException {
        Consumer consumer = new Consumer();
        for (int i = 0; i < 4; i++) {
            new Thread(
                    ()-> consumer.getMessage(ActiveMqConfig.DEFAULT_QUEUE_NAME)
            ).start();
        }
    }
}
