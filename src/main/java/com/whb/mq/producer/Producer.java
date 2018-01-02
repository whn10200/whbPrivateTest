package com.whb.mq.producer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.whb.mq.bean.TestMqMessage;
import com.whb.mq.helper.ActiveMqHelper;

@Component("queueSender")
public class Producer {

	
	@Resource   //(name="jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送一条消息到指定队列
     * @param queueName  队列名称
     * @param message  消息内容
     */
    public void send(String queueName,final String message){
        jmsTemplate.send(queueName, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
    
    @Scheduled(fixedDelay=3000)//每3s执行1次
    public void send() {
    	TestMqMessage message = new TestMqMessage();
    	message.setSendText("生产者");
        ActiveMqHelper.sendToQueue(message);
    }

}
