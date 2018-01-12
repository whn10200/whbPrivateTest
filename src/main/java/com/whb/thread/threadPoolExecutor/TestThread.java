package com.whb.thread.threadPoolExecutor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThread {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ConcurrentHashMap<K, V>
		/*SimpleDateFormat sdf = new SimpleDateFormat();
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 0, null, null);*/
		
		ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return taskID;
                }
            });
        }
        for(int i = 1; i < 5; i++) {
            try {
                System.out.println(cs.take().get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m){
		return null;
	}

}
