package com.whb.dubbo.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.annotation.Reference;
import com.whb.dubbo.product.service.ComputeService;

@SpringBootApplication
//@ImportResource({"classpath:dubbo.xml"})
public class ApplicationConsumerTest {
	
	@Reference(version = "1.0")
	private ComputeService ComputeService;

}
