package com.github.shiayanga.k8s.util;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

public class ConsumerAcl {
    public static void main(String[] args) {
        //初始化参数
        String userName = "testUser";
        String pwd = "LxjKqWTuEg92";
        String topic = "testTopic1";
        String nots = "192.168.28.75:9092";

        Properties props = new Properties();
        props.put("bootstrap.servers", nots);
        props.put("group.id", "testnet");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "latest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //用户名密码方式 begin
        props.put("sasl.jaas.config","org.apache.kafka.common.security.scram.ScramLoginModule required username=\""+userName+"\" password=\""+pwd+"\";");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        //用户名密码方式 end

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
/*        Map<String, List<PartitionInfo>> topics = kafkaConsumer.listTopics();
        Set<String> keySet = topics.keySet();
        for (String key : keySet) {
            System.out.println(key + " >>> " + topics.get(key));
        }*/
        kafkaConsumer.subscribe(Arrays.asList(topic));
        System.out.println("开始接收数据:" + new Date());
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(10);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("receive messgae: partition: " + record.partition() + "offset = " + record.offset() + ", value =" + record.value());
            }
        }
    }
}