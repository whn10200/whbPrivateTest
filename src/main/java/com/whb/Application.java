package com.whb;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(value = {"com.whb.redis","com.whb.redis.config"})
public class Application {
	
	@Bean
    public Queue queue() {
       return new ActiveMQQueue("test.queue");
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
