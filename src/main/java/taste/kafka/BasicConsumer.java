package taste.kafka;

import org.apache.kafka.clients.consumer.*;
import share.Person;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author: kkyeer
 * @Description: 消费者
 * @Date:Created in 23:01 1-2
 * @Modified By:
 */
class BasicConsumer {
    private static final String SERVER = "192.168.137.189:9092";

    public static void main(String[] args) {
        Consumer<String, Person> personConsumer = personConsumer();
        personConsumer.subscribe(Collections.singletonList("ha"));
        while (true) {
            ConsumerRecords<String, Person> records = personConsumer.poll(Duration.of(100, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, Person> record : records) {
                System.out.println("partition:" + record.partition());
                System.out.println(record.value());
            }
        }
    }


    private static Consumer<String, Person> personConsumer(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, CustomPersonSerializer.class.getCanonicalName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomPersonSerializer.class.getCanonicalName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "gr");
        properties.put("auto.offset.reset","earliest ");
        return new KafkaConsumer<>(properties);
    }
}
