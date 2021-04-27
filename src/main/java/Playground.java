import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 代码操场随便玩
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */

public class Playground {
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {

        System.out.println(Long.toBinaryString(1384076889872744455L>>12));
    }

    protected static   long getMaxWorkerId(long datacenterId,String pid, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        mpid.append(pid);
        long workerId = (long)(mpid.toString().hashCode() & '\uffff') % (maxWorkerId + 1L);
        System.out.println(workerId);
        return workerId;
    }

    private static void localNetAddress() throws UnknownHostException {
        // TODO
        System.out.println(Inet4Address.getLocalHost().getCanonicalHostName());
        // 获取IP
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        //  TODO
        System.out.println(Inet4Address.getLocalHost().getHostName());
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
