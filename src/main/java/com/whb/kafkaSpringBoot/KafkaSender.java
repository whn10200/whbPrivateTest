package com.whb.kafkaSpringBoot;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author whb
 * @date 2018年1月22日 下午8:40:20 
 * @Description: 消息发送类 KafkaSender.java
 */
@Component
public class KafkaSender {
	
	private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate ;

    private Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send() {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        
        String topic = "zhisheng";
        String msg = gson.toJson(message);
        
        //log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
        kafkaTemplate.send(topic, msg);
        
        //发送消息，topic不存在将自动创建新的topic
        //topic：主题名称；partition：要发送消息到哪个分区；timestamp：创建消息的时间；key：消息的键；value：消息的值。 
        //send方法是异步，一旦将消息保存在等待发送消息的缓存中就立即返回，这样就不会阻塞去等待每一条消息的响应。可以使用listenableFuture.cancle()方法去取消消息的发送
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic,msg);
       
        //添加成功发送消息的回调和失败的回调
        listenableFuture.addCallback(
                result -> logger.info("send message to {} success",topic),
                ex -> logger.info("send message to {} failure,error message:{}",topic,ex.getMessage()));

    }
}
