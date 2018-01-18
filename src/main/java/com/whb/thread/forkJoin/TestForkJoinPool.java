package com.whb.thread.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author whb
 * @date 2018年1月15日 下午5:47:55 
 * @Description: 简单的Join/Fork计算过程，将1—1001数字相加
 */
public class TestForkJoinPool {

	private static final Integer MAX = 500;

    static class MyForkJoinTask extends RecursiveTask<Integer> {
        /**@Fields serialVersionUID 
		 */
		private static final long serialVersionUID = 1L;

		// 子任务开始计算的值
        private Integer startValue;

        // 子任务结束计算的值
        private Integer endValue;
        
        private Integer type;

        public MyForkJoinTask(Integer startValue , Integer endValue, Integer type) {
            this.startValue = startValue;
            this.endValue = endValue;
            this.type = type;
            //System.out.println(Thread.currentThread().getId());
        }

        @Override
        protected Integer compute() {
            // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
            // 可以正式进行累加计算了
            if(endValue - startValue < MAX) {
                System.out.println("type="+type+"=;当前线程id="+Thread.currentThread().getId()+"开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
                Integer totalValue = 0;
                /*for(int index = this.startValue ; index <= this.endValue  ; index++) {
                    totalValue += index;
                }*/
                try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                return totalValue;
            }
            // 否则再进行任务拆分，拆分成两个任务
            else {
                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2, 1);
                subTask1.fork();
                MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1 , endValue, 2);
                subTask2.fork();
                return subTask1.join() + subTask2.join();
                
            }
        }
    }

    public static void main(String[] args) {
        // 这是Fork/Join框架的线程池
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture =  pool.submit(new MyForkJoinTask(1,1001, 0));
        try {
            Integer result = taskFuture.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
    }
}
