package com.whb.thread.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.whb.model.Student;

public class OtherTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCAS() {
		AtomicInteger acint = new AtomicInteger();
		acint.incrementAndGet();
		System.out.println(acint.get());
		System.out.println(acint.getAndIncrement());
	}
	
	@Test
	public void lockTest(){
		Lock lock = new ReentrantLock();
		
		Thread.interrupted();
		
		Student tt = null;
	}
	

}
