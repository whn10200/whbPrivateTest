package com.whb.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.whb.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class RedisLockTest {

	@Resource(name ="whbRedisTempalte")
	private WhbRedisTempalte whbRedisTempalte;
	
	@Resource(name = "cloudRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	
	@Test
	public void test() {
		 String redisValue = stringRedisTemplate.opsForValue().get(String.valueOf("LOAN_AUDIT_"));

         if(StringUtils.isBlank(redisValue)){
        	 //保持10秒
        	 stringRedisTemplate.opsForValue().set("LOAN_AUDIT_".toString(), "whb".toString(), 10, TimeUnit.SECONDS);
         }else{
        	 System.out.println("不存在");
         }
	}
	

	/**
	 * 分布式锁测试
	 * @throws Exception
	 * @return void
	 */
	@Test 
	public void lockTest() throws Exception {
		new RedisLock(whbRedisTempalte) {

			public Object apply() throws Exception {

				System.out.println("AAAAA");

				return null;

			}
			
			@RedisLock.Schema("A_ACCOUNT_LOCK_1")
			public Object key() {
				return "id";
			}
		};
	}

}
