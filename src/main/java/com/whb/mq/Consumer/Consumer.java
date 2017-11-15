package com.whb.mq.Consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author whb
 * @date 2017年11月15日 上午11:37:21 
 * @Description: 消费者
 */
@Component
public class Consumer {
	
	@JmsListener(destination = "test.queue")
    public void receiveQueue(String text) {
       System.out.println(text);
    }

}
