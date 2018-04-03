package com.whb.thread.test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author whb
 * @date 2018年3月24日 下午2:39:02
 * @Description: 可重入锁的使用
 */
public class ReentrantLockTest {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private Lock lock = new ReentrantLock(); // 注意这个地方

	@Test
	public void lockTest() throws InterruptedException {
		final ReentrantLockTest test = new ReentrantLockTest();

		new Thread() {
			public void run() {
				test.lock(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				test.lock(Thread.currentThread());
			};
		}.start();
		
		Thread.sleep(1000*5);
		System.out.println(arrayList.toString());
		
	}
	
	/**
	 * lockInterruptibly()响应中断的使用方法：
	 */
	@Test
	public void lockInterruptiblyTest(){
		ReentrantLockTest test = new ReentrantLockTest();
        MyThread thread1 = new MyThread(test);
        MyThread thread2 = new MyThread(test);
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
	}
	
	public void lockInterruptibly(Thread thread) throws InterruptedException{
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {  
            System.out.println(thread.getName()+"得到了锁");
            long startTime = System.currentTimeMillis();
            for(    ;     ;) {
                if(System.currentTimeMillis() - startTime >= 10000)
                    break;
                //插入数据
            }
        }
        finally {
            System.out.println(Thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }  
    }
	
	class MyThread extends Thread {
	    private ReentrantLockTest test = null;
	    public MyThread(ReentrantLockTest test) {
	        this.test = test;
	    }
	    @Override
	    public void run() {
	         
	        try {
	            test.lockInterruptibly(Thread.currentThread());
	        } catch (InterruptedException e) {
	            System.out.println(Thread.currentThread().getName()+"被中断");
	        }
	    }
	}
	

	/**
	 * 在insert方法中的lock变量是局部变量，每个线程执行该方法时都会保存一个副本，
	 * 那么理所当然每个线程执行到lock.lock()处获取的是不同的锁，所以就不会发生冲突。
	 * @param thread
	 * @return void
	 */
	public void lock(Thread thread) {
		//Lock lock = new ReentrantLock();不能在此实例化
		lock.lock();
		try {
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0; i < 5; i++) {
				arrayList.add(i);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			System.out.println(thread.getName() + "释放了锁");
			lock.unlock();
		}
	}
	
	public void tryLock(Thread thread) {
        if(lock.tryLock()) {
            try {
                System.out.println(thread.getName()+"得到了锁");
                for(int i=0;i<5;i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                System.out.println(thread.getName()+"释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName()+"获取锁失败");
        }
    }

}
