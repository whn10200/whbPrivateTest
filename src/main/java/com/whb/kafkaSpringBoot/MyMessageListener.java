package com.whb.kafkaSpringBoot;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class MyMessageListener implements MessageListener<String, String>{
	
	public final static Logger logger = LoggerFactory.getLogger(MyMessageListener.class);

	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		try {
			String topic = data.topic();//消费的topic
			logger.info("-------------recieve message from {} topic-------------", topic);
			logger.info("partition:{}", String.valueOf(data.partition()));//消费的topic的分区
			logger.info("offset:{}", String.valueOf(data.offset()));//消费者的位置
			logger.info("get message from {} topic : {}", topic, data.value());//接收到的消息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
