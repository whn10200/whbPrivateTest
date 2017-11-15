package com.whb;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

public class Application {
	
	@Bean
    public Queue queue() {
       return new ActiveMQQueue("test.queue");
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
