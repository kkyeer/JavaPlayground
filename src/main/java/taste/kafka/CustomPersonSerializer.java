package taste.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import share.Person;

import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 自定义序列化与反序列化
 * @Date:Created in 11:56 1-1
 * @Modified By:
 */
public class CustomPersonSerializer implements Serializer<Person>, Deserializer<Person> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Person data) {

        return JSON.toJSONString(data).getBytes();
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Person data) {
        return serialize(topic,data);
    }

    @Override
    public void close() {

    }

    @Override
    public Person deserialize(String topic, byte[] data) {

        return JSON.parseObject(data, Person.class);
    }

    @Override
    public Person deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }
}
