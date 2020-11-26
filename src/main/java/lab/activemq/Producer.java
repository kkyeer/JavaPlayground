package lab.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: kkyeer
 * @Description: 生产者
 * @Date:Created in 17:04 2019/8/29
 * @Modified By:
 */
class Producer {
    private final ConnectionFactory CONNECTION_FACTORY;
    private final Connection CONNECTION;
    private final Session SESSION;
    private ThreadLocal<MessageProducer> messageProducerThreadLocal = new ThreadLocal<>();
    private static final ConcurrentHashMap<String, Queue> QUEUE_MAP = new ConcurrentHashMap<>();

    Producer() throws JMSException {
        this.CONNECTION_FACTORY = new ActiveMQConnectionFactory(ActiveMqConfig.USERNAME, ActiveMqConfig.PASSWORD, ActiveMqConfig.BROKER_URL);
        this.CONNECTION = this.CONNECTION_FACTORY.createConnection();
        this.CONNECTION.start();
        this.SESSION = this.CONNECTION.createSession(true, Session.SESSION_TRANSACTED);
    }

    void sendMessage(String queueName, String message) {
        MessageProducer messageProducer = messageProducerThreadLocal.get();
        if (messageProducer == null) {
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
                messageProducer = SESSION.createProducer(queue);
                messageProducerThreadLocal.set(messageProducer);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        try {
            Message message1 = SESSION.createTextMessage(Thread.currentThread().getName() + "-" + message);
            messageProducer.send(message1);
            SESSION.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JMSException {
        Producer producer = new Producer();
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            new Thread(
                    ()->{
                        for (int j = 0; j < 10; j++) {
                            producer.sendMessage(ActiveMqConfig.DEFAULT_QUEUE_NAME,"MSG_"+ finalI +"_"+j);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }
    }
}
