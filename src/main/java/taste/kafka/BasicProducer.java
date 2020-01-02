package taste.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import share.Person;

import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: kkyeer
 * @Description: 基本使用
 * @Date:Created in 23:50 12-31
 * @Modified By:
 */
public class BasicProducer {
    private static final String SERVER = "192.168.137.189:9092";
    public static void main(String[] args) {
//        basicProduce();
        personProduce();
    }


    private static void personProduce(){
        Producer<String, Person> personProducer = buildPersonProducer();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        while (scanner.hasNext()) {
            Person p = new Person();
            p.setAge(random.nextInt(100));
            p.setName(scanner.next());
            ProducerRecord<String, Person> record = new ProducerRecord<>("ha",  p);
            personProducer.send(record,(metaData,ex)->{
                System.out.println("Async");
                System.out.println("Offset:" + metaData.offset());
                System.out.println("Partition:" + metaData.partition());
            });
        }
    }

    private static void basicProduce(){
        Producer<String, String> basicProducer = buildProducer();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String value = scanner.next();
            ProducerRecord<String, String> record = new ProducerRecord<>("ha",  value);
            sendMsgAsync(basicProducer, record);
//            sendMsgSync(basicProducer, record);
        }
    }

    /**
     * 异步发送
     * @param producer
     * @param record
     */
    private static void sendMsgAsync(Producer<String,String> producer, ProducerRecord<String,String> record){
        producer.send(record, (metadata, exception)->{
            System.out.println("Async");
            System.out.println("Offset:" + metadata.offset());
            System.out.println("Partition:" + metadata.partition());
        });
    }

    /**
     * 同步发送
     * @param producer
     * @param record
     */
    private static void sendMsgSync(Producer<String,String> producer, ProducerRecord<String,String> record){
            Future<RecordMetadata> response =  producer.send(record);
            RecordMetadata metadata = null;
            try {
                metadata = response.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(metadata);
    }

    /**
     * 构建producer
     * @return
     */
    private static Producer<String, String> buildProducer(){
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        return new KafkaProducer<>(kafkaProperties);
    }

    /**
     * 构建自定义Partitioner和自定义序列化的Producer
     */
    static Producer<String,Person> buildPersonProducer(){
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, CustomPersonSerializer.class.getCanonicalName());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomPersonSerializer.class.getCanonicalName());
        kafkaProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getCanonicalName());
        return new KafkaProducer<>(kafkaProperties);
    }
}
