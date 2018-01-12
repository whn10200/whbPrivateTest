package com.whb.thread.threadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.whb.model.Student;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		List<Student> infoList = new ArrayList<>();
		infoList.add(new Student(11, "123"));
		infoList.add(new Student(22, "456"));
		infoList.add(new Student(33, "789"));

		List<Future<String>> returnFuture = new ArrayList<>();
		int processorsNumber = Runtime.getRuntime().availableProcessors();
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("loan-pool-%d").build();
		ExecutorService pool = new ThreadPoolExecutor(processorsNumber, 2*processorsNumber, 60L, TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		for (Student stu : infoList) {
//			returnFuture.add(pool.submit(()-> str+Thread.currentThread().getName()));
			pool.execute(()-> 
				{	
//					try {
//						Thread.sleep(2000L);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
					stu.setName(Thread.currentThread().getName()+stu.getName());
				});
		}
		pool.shutdown();   
		
		for (Student stu : infoList) {
			System.out.println(stu.getName());
		}
		
	}
	
	
	

}
