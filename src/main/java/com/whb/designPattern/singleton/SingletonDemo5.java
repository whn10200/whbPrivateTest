package com.whb.designPattern.singleton;

/**
 * @author whb
 * @date 2018年3月5日 下午8:03:25
 * @Description: 双重校验锁
 */
public class SingletonDemo5 {

	//volatile 关键字修饰，确保线程安全
	private volatile static SingletonDemo5 singletonDemo5;

	private SingletonDemo5() {
	}

	public static SingletonDemo5 getSingletonDemo5() {
		if (singletonDemo5 == null) {
			synchronized (SingletonDemo5.class) {
				if (singletonDemo5 == null) {
					singletonDemo5 = new SingletonDemo5();
				}
			}
		}
		return singletonDemo5;
	}

}
