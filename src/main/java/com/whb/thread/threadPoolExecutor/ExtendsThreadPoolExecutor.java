package com.whb.thread.threadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author whb
 * @date 2018年1月8日 下午3:48:50 
 * @Description: 手动编写扩展类
 */
public class ExtendsThreadPoolExecutor extends ThreadPoolExecutor {
	
	private static Log LOGGER = LogFactory.getLog(ExtendsThreadPoolExecutor.class);

	public ExtendsThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		TestRunnable testRunnable = (TestRunnable)r;
        ExtendsThreadPoolExecutor.LOGGER.info("beforeExecute(Thread t, Runnable r) : " + testRunnable.getIndex());
	}



	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		TestRunnable testRunnable = (TestRunnable)r;
        ExtendsThreadPoolExecutor.LOGGER.info("afterExecute(Runnable r, Throwable t) : " + testRunnable.getIndex());
	}



	@Override
	protected void terminated() {
		ExtendsThreadPoolExecutor.LOGGER.info("terminated() ！！");
	}



	public static void main(String[] args) throws InterruptedException {
		// 这个做法，是故意让后续index == 5 - 9的线程进入等待队列。以便观察执行现象
		// 如果队列的长度为4，就会有一个任务被拒绝执行，报RejectedExecutionException异常
        ExtendsThreadPoolExecutor extendsPool = new ExtendsThreadPoolExecutor(5, 5, 6000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
        for(int index = 0 ; index < 10 ; index ++) {
            // 一定要使用execute，不要使用submit。后文讲解原因
            extendsPool.execute(new TestRunnable(index));
            System.out.println("========"+extendsPool.getActiveCount());
        }

        // 发出停止指令。注意停止指令本身不会等待，要使用awaitTermination进行等待。
        // 注意，按照我们上文讲过的线程池的工作原理，线程池在收到shutdown终止指令后
        // 就不会再接受提交过来的任务了，无论“核心线程”、等待队列处于什么样的状态！
        extendsPool.shutdown();
        // 当所有任务执行完成后，终止线程池的运行
        extendsPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

	}
	
	
	/**
     * 这个就是测试用的线程
     */
    private static class TestRunnable implements Runnable {

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
             * 线程中，就只做一件事情：
             * 等待10秒钟的事件，以便模拟业务操作过程
             * */
            Thread currentThread  = Thread.currentThread();
            synchronized (currentThread) {
                try {
                    currentThread.wait(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

}
