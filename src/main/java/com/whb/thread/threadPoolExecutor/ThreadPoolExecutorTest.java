package com.whb.thread.threadPoolExecutor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.whb.demo.Student;

public class ThreadPoolExecutorTest {

	@Test
	public void test() {
		/*
		 * corePoolSize：核心大小，线程池初始化的时候，就会有这么大 maximumPoolSize：线程池最大线程数
		 * keepAliveTime：如果当前线程池中线程数大于corePoolSize。
		 * 多余的线程，在等待keepAliveTime时间后如果还没有新的线程任务指派给它，它就会被回收
		 * 
		 * unit：等待时间keepAliveTime的单位
		 * 
		 * workQueue：等待队列。这个对象的设置是本文将重点介绍的内容
		 */
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES,
				new SynchronousQueue<Runnable>());
		for (int index = 0; index < 10; index++) {
			poolExecutor.submit(new ThreadPoolExecutorTest.TestRunnable(index));
		}
		
		// 没有特殊含义，只是为了保证线程不会退出
		synchronized (poolExecutor) {
			try {
				poolExecutor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 这个就是测试用的线程
	 */
	private static class TestRunnable implements Runnable {

		/**
		 * 日志
		 */
		private static Log LOGGER = LogFactory.getLog(TestRunnable.class);

		/**
		 * 记录任务的唯一编号，这样在日志中好做识别
		 */
		private Integer index;

		public TestRunnable(int index) {
			this.index = index;
		}

		/**
		 * @return the index
		 */
		public Integer getIndex() {
			return index;
		}

		@Override
		public void run() {
			/*
			 * 线程中，就只做一件事情： 等待60秒钟的事件，以便模拟业务操作过程
			 */
			Thread currentThread = Thread.currentThread();
			TestRunnable.LOGGER.info("线程：" + currentThread.getId() + " 中的任务（" + this.getIndex() + "）开始执行===");
			synchronized (currentThread) {
				try {
					currentThread.wait(6000);
				} catch (InterruptedException e) {
					TestRunnable.LOGGER.error(e.getMessage(), e);
				}
			}
			TestRunnable.LOGGER.info("线程：" + currentThread.getId() + " 中的任务（" + this.getIndex() + "）执行完成");
		}

	}

	
	@Test
	public void queueTest() throws InterruptedException {
		
		
		/*3、 LinkedBlockingDeque是一个基于链表的双端队列。LinkedBlockingQueue的内部结构决定了它只能从队列尾部插入，
		从队列头部取出元素；但是LinkedBlockingDeque既可以从尾部插入/取出元素，还可以从头部插入元素/取出元素。*/
		LinkedBlockingDeque<Student> linkedDeque = new LinkedBlockingDeque<Student>();
		// push ，可以从队列的头部插入元素
		linkedDeque.push(new Student(1));
		linkedDeque.push(new Student(2));
		linkedDeque.push(new Student(3));
		// poll ， 可以从队列的头部取出元素
		Student tempObject = linkedDeque.poll();
		// 这里会打印 tempObject.index = 3
		System.out.println("tempObject.index = " + tempObject.getAge());

		// put ， 可以从队列的尾部插入元素
		linkedDeque.put(new Student(4));
		linkedDeque.put(new Student(5));
		// pollLast , 可以从队列尾部取出元素
		tempObject = linkedDeque.pollLast();
		// 这里会打印 tempObject.index = 5
		System.out.println("tempObject.index = " + tempObject.getAge());
	}
	
}
