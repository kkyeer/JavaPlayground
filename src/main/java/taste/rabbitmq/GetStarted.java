package taste.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Author: kkyeer
 * @Description: 基本用法
 * @Date:Created in 17:49 2019/8/24
 * @Modified By:
 */
public class GetStarted {
    private static final String QUEUE_NAME = "test_queue";
    private static Connection getConnection() throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.137.89");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("testhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory.newConnection();
    }

    private static void send() throws  Exception{
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello rabbit mq";
        new Thread(
                ()->{
                    for (int i = 0; i < 10; i++) {
                        try {
                            channel.basicPublish("", QUEUE_NAME, null, (message+i).getBytes());
                            Thread.sleep(1000);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        channel.close();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                    try {
                        connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private static void receive() throws Exception{
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received message:" + message);
        };
        channel.basicConsume(QUEUE_NAME, true,deliverCallback,consumerTag -> {});
        System.out.println("WAITING");
    }

    public static void main(String[] args) throws Exception {
        send();
        receive();
    }
}
