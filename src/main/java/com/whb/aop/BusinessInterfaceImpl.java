package com.whb.aop;

public class BusinessInterfaceImpl implements BusinessInterface {

	@Override
	public void dosomething(String username) {
		System.out.println("==================");
		
		System.out.println("正在为用户：" + username + "，进行真实的业务处理。。。");
	}

}
