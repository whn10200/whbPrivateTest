package com.whb.rpc.product;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHi(String name) {
		return "你好"+name;
	}

}
