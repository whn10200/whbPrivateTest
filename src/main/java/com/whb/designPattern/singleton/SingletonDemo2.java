package com.whb.designPattern.singleton;

/**
 * @author whb
 * @date 2018年3月5日 下午8:03:27 
 * @Description: 饿汉模式
 */
public class SingletonDemo2 {
	
	private static SingletonDemo2 instance = new SingletonDemo2();

	private SingletonDemo2() {
	}
	
	/**
	 * 基于classloder机制避免了多线程的同步问题，不过，instance在类装载时就实例化，这时候初始化instance显然没有达到lazy loading的效果。
	 * @return
	 * @return SingletonDemo2
	 */
	public static SingletonDemo2 getInstance1(){
		return instance;
	}

}
