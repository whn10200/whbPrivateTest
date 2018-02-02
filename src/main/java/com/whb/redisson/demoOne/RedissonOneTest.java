package com.whb.redisson.demoOne;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.whb.Application;

/**
 * @author whb
 * @date 2018年2月2日 下午4:30:14 
 * @Description: 基于高版本的测试类
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedissonOneTest {
	
	/**
     * 线程等待时间 10s
     */
    private static final int WAIT_TIME = 10;

	@Autowired
    RedissonClient redissonClient;
	
	public void getLock(){
		RLock lock = redissonClient.getLock("123");
		try{
			boolean b = lock.tryLock(5, 20, TimeUnit.SECONDS);
			if(!b){
				throw new RuntimeException("-------------");
			}
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 分布式锁测试
	 * @return void
	 */
	@Test
	public void getFairLock() {
		RLock fairLock = redissonClient.getFairLock("redisKey");

		try {// 加锁 防止其他更新资金日志情况
			if (fairLock.tryLock(WAIT_TIME, TimeUnit.SECONDS)) {
				Thread.sleep(500L);
			}
		} catch (InterruptedException e) {
			/* 抛出runtimeException是因为在消费者处理业务后写资金日志导致时,
			 * 默认程序会捕捉BizFeignException,
			 * 导致异常无法发送邮件通知,因此采用RunTimeException过滤
			 * */
			throw new RuntimeException("获取资金账户信息异常", e);
		} finally {
			fairLock.unlock();
		}
	}


    @Test
    public void getAtomicLong() {
    	RAtomicLong num = redissonClient.getAtomicLong("test333");
    	System.out.println(num.get());
    }

    @Test
    public void getAndIncrement() {
        RAtomicLong num = redissonClient.getAtomicLong("test");
        num.getAndIncrement();
    }

    @Test
    public void delete() {
        RAtomicLong num = redissonClient.getAtomicLong("test");
        num.delete();
    }

    @Test
    public void threadTest() {
        ExecutorService executors = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5; i++) {
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    RAtomicLong num = redissonClient.getAtomicLong("test1233");

                    System.out.println(Thread.currentThread().getName() + " " + num.getAndIncrement());

                }
            });
        }
    }

}
