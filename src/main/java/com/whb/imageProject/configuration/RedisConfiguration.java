package com.whb.imageProject.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.whb.imageProject.service.iface.redis.IBinaryJedisCluster;
import com.whb.imageProject.service.impl.redis.BinaryJedisClusterImpl;

import redis.clients.jedis.HostAndPort;

/**
 * 基于RedisProperties的配置信息
 * @author yinwenjie
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {
	
	@Autowired
	private RedisProperties redisProperties;
	
	/**
	 * 获取redis cluster客户端
	 * @return
	 */
	@Bean
	public IBinaryJedisCluster getBinaryJedisCluster() {
		List<String> addresses = this.redisProperties.getAddress();
		Set<HostAndPort> addressSet = new HashSet<HostAndPort>();
		// server的地址和端口
		for (String address : addresses) {
			String addressArray[] = address.split("\\:");
			HostAndPort hostAndPort = new HostAndPort(addressArray[0], Integer.parseInt(addressArray[1]));
			addressSet.add(hostAndPort);
		}
		
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
		poolConfig.setMaxTotal(redisProperties.getMaxTotal());
		poolConfig.setMinIdle(redisProperties.getMinIdle());
		poolConfig.setMaxIdle(redisProperties.getMaxIdle());
		
		// 初始化redis cluster服务对象
		IBinaryJedisCluster jedisCluster = new BinaryJedisClusterImpl(addressSet , 10000 , poolConfig);
		return jedisCluster;
	}
}
