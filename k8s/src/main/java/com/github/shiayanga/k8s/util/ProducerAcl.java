package com.github.shiayanga.k8s.util;

import java.util.Properties;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import kafka.zk.KafkaZkClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerAcl {
    public static void main(String[] args) {

        //初始化参数
        String userName = "testUser";
        String pwd = "LxjKqWTuEg92";
        String topic = "testTopic1";
        String nots = "172.24.128.45:32041,172.24.128.72:32119,172.24.128.55:32001";

        Properties props = new Properties();
        props.put("bootstrap.servers", nots);
        props.put("acks", "1");//ack方式，all，会等所有的commit最慢的方式
        props.put("retries ", 1);//失败是否重试，设置1会有可能产生重复数据
        props.put("buffer.memory", 102400);//整个producer可以用于buffer的内存大小
        props.put("linger.ms", 0);  //等多久，如果buffer没满，比如设为1，即消息发送会多1ms的延迟，如果buffer没满
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //用户名密码方式 begin
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"" + userName + "\" password=\"" + pwd + "\";");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        //用户名密码方式 end

        //生产者发送消息
        System.out.println("send message start: " + new Date());
        Producer<String, String> producer = new KafkaProducer<>(props);
        Long startTime = System.currentTimeMillis();

        int i = 0;
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            String str = "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM";
            String value = str + i;
            ProducerRecord<String, String> msg = new ProducerRecord<>(topic, value);
            //producer.send
            producer.send(msg);
            System.out.println("send message: " + i);
        }

//        System.out.println("send message end .");
//        producer.close();
//
//        System.out.println("send message over.");
//        producer.close(100, TimeUnit.MILLISECONDS);
    }
}