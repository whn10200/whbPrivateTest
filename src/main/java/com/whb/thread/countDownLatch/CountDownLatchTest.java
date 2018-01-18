package com.whb.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

import com.whb.io.SocketClientRequestThread;

public class CountDownLatchTest {

	public static void main(String[] args) throws InterruptedException {
		
		Integer clientNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);

        //分别开始启动这20个客户端
        for(int index = 0 ; index < clientNumber ; index++ , countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, index);
            new Thread(client).start();
        } 

        //这个wait不涉及到具体的实验逻辑，只是为了保证守护线程在启动所有线程后，进入等待状态
        synchronized (CountDownLatchTest.class) {
        	CountDownLatchTest.class.wait();
        }

	}

}
