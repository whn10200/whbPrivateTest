package com.whb.test;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Lock lock = new ReentrantLock();
//		lock.lock();
		System.out.println(123);
		try {
			System.out.println(456);
		} finally {
			System.out.println(789);
//			lock.unlock();
		}*/
		
		Map<String,String> map = new ConcurrentHashMap();
		
		Map<String,String> mapTable = new Hashtable<String, String>();
		
		ReadWriteLock lock = new ReentrantReadWriteLock();
			
		

	}

}
