import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: kkyeer
 * @Description: 代码操场随便玩
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */

public class Playground {
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
        sendMsgAsync();
    }

    private static void sendMsgAsync(){
        Producer<String, String> producer = buildProducer();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String value = scanner.next();
            ProducerRecord<String, String> record = new ProducerRecord<>("test", 0, "Key", value);
            Future<RecordMetadata> response =  producer.send(record, (metadata,exception)->{
                System.out.println("Async meta:" + metadata.offset());
            });
            RecordMetadata metadata = null;
            try {
                metadata = response.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(metadata);
        }
    }

    private static void sendMsgSync(){
        Producer<String, String> producer = buildProducer();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String value = scanner.next();
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("test", 0,"Key",value);
            Future<RecordMetadata> response =  producer.send(record);
            RecordMetadata metadata = null;
            try {
                metadata = response.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(metadata);
        }
    }

    private static Producer<String,String> buildProducer(){
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", "192.168.137.189:9092");
        kafkaProperties.put("key.serializer", StringSerializer.class.getCanonicalName());
        kafkaProperties.put("value.serializer", StringSerializer.class.getCanonicalName());
        return new KafkaProducer<>(kafkaProperties);

    }

    private static void getAddress() throws UnknownHostException {
        InetAddress inetAddress = Inet4Address.getLocalHost();
        System.out.println(inetAddress.getCanonicalHostName());
    }
}
