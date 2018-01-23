package com.whb.kafkaSpringBoot;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author whb
 * @date 2018年1月22日 下午8:41:04 
 * @Description: 消息接收类 KafkaReceiver.java
 */
@Component
@Slf4j
public class KafkaReceiver {

	//@KafkaListener(topics = {"whb","tian"})
    @KafkaListener(topics = {"whb"})
    public void listen(ConsumerRecord<?, ?> record) throws Exception {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            System.out.println(message.toString());
 
            //log.info("----------------- record =" + record);
            //log.info("------------------ message =" + message);
        }

    }
}
