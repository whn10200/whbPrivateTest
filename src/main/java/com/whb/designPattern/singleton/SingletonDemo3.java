package com.whb.designPattern.singleton;

/**
 * @author whb
 * @date 2018年3月5日 下午8:03:27
 * @Description: 饿汉模式
 */
public class SingletonDemo3 {

	private static SingletonDemo3 instance = null;

	private SingletonDemo3() {
	}

	static {
		instance = new SingletonDemo3();
	}

	/**
	 * 和SingletonDemo2差不多，都是在类初始化即实例化instance
	 * 
	 * @return
	 * @return SingletonDemo2
	 */
	public static SingletonDemo3 getInstance1() {
		return instance;
	}

}
