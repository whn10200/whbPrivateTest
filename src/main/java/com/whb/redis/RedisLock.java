package com.whb.redis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class RedisLock {
	
	WhbRedisTempalte redis;

	private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

	final long TIME_OUT = 2 * 60 * 1000;// 分钟

	final long START_TIME = System.currentTimeMillis();

	private long TIME = 0;

	private Object t;

	private String key;

	// 业务key
	public abstract Object key();

	// 业务实现
	public abstract Object apply() throws Exception;
	
	//获取apply的返回值，根据情况使用
	public Object get() {
		return t;
	};

	public RedisLock(WhbRedisTempalte redis) throws Exception {

		this.redis = redis;

		setKey();

		execute();

	}

	// 业务类型 + key 可以控制锁的力度
	public void setKey() throws Exception {

		this.key = getScheme() + key();

	};

	public String getScheme() throws Exception {

		Schema anno = getClass().getMethod("key").getAnnotation(Schema.class);

		if (anno == null)

			throw new Exception("key 方法 缺少必要的 注解  @Schemer");

		if (anno.value() == null)

			throw new Exception("注解 @Schemer 缺少必要的参数");

		return anno.value();

	}

	

	public void execute() throws Exception {

		while (TIME < TIME_OUT){
			if (work()){
				break;
			}
		}
	}

	public boolean work() throws Exception {

		TIME = System.currentTimeMillis() - START_TIME;

		if (lock()) {

			try {

				t = apply();

				return true;

			} finally {

				unlock();

			}

		} else {

			try {

				Thread.sleep(500);

			} catch (InterruptedException e) {

				log.error(e.getMessage());

			}

		}

		return false;

	}

	public boolean lock() {

		try {

			Boolean value = redis.setNx(key, String.valueOf(START_TIME));

			if (value) {

				redis.expire(key, TIME_OUT);

				log.debug("\n 加锁成功 : key={} 持有者 ={}", key, START_TIME);

				return true;

			} else {

				log.debug("\n 加锁失败 : key={} 持有者 ={}", key, START_TIME);

				return false;

			}

		} catch (Exception e) {

			log.error(e.getMessage());

		}

		return false;
	}

	public void unlock() {
		try {

			// 同一持有者才能 解锁
			if (redis.get(key).equals(String.valueOf(START_TIME))) {

				redis.del(key);

				log.debug("\n 解锁成功 : key={} 持有者 ={}", key, START_TIME);

				return;

			}

			log.debug("\n 解锁失败 : key={} 持有者 {}", key, START_TIME);

		} catch (Exception e) {

			log.error(e.getMessage());

		}

	}

	@Target({ ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Schema {

		String value();// 业务类型

	}

	public static void main(String[] args) throws Exception {
		
		WhbRedisTempalte redisTempalte = null;
		
		//传入WhbRedisTempalte对象实例
		new RedisLock(redisTempalte) {
			
			//业务模块
			@RedisLock.Schema("A_ACCOUNT_LOCK_")
			public Object key() {
				
				//锁定的业务id
				return "id";

			}
			
			
           
			public Object apply() throws Exception {

				 //业务实现
				System.out.println("apply()");

				return null;

			}

		}
		
		//.get() 获取apply的返回值，根据情况使用
		
		;
	}

}
