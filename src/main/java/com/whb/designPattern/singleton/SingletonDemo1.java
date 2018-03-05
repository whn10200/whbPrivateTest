package com.whb.designPattern.singleton;

/**
 * @author whb
 * @date 2018年3月5日 下午7:55:33
 * @Description: 懒汉模式
 */
public class SingletonDemo1 {

	private static SingletonDemo1 instance;

	private SingletonDemo1() {
	}

	/**
	 * 懒汉，懒加载模式，缺点是多线程不安全
	 * @return
	 * @return SingletonDemo1
	 */
	public static SingletonDemo1 getInstance1() {
		if (instance == null) {
			instance = new SingletonDemo1();
		}
		return instance;
	}
	
	/**
	 * 线程安全模式，但是性能不足
	 * @return
	 * @return SingletonDemo1
	 */
	public static synchronized SingletonDemo1 getInstance2() {
		if (instance == null) {
			instance = new SingletonDemo1();
		}
		return instance;
	}
	
}
