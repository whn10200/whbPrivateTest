package com.whb.imageProject.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 整个redis服务节点的配置信息描述
 * @author yinwenjie
 */
@Component
@ConfigurationProperties(locations={"classpath:redisCluster.yaml"} , prefix="redis")
public class RedisProperties {
	/**
	 * 最大等待时间
	 */
	private int maxWaitMillis;
	/**
	 * 最大连接数
	 */
	private int maxTotal;
	/**
	 * 最小增加数
	 */
	private int minIdle;
	/**
	 * 最大增加数
	 */
	private int maxIdle;
	
	/**
	 * redis服务节点连接列表
	 */
	private List<String> address;
	
	/**
	 * 单个key的过期时间，单位为秒。
	 * 默认为5分钟，也就是 60 * 5;
	 */
	private int keyExpiredTime = 300; 
	
	/**
	 * @return the maxWaitMillis
	 */
	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	/**
	 * @param maxWaitMillis the maxWaitMillis to set
	 */
	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	/**
	 * @return the maxTotal
	 */
	public int getMaxTotal() {
		return maxTotal;
	}

	/**
	 * @param maxTotal the maxTotal to set
	 */
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	/**
	 * @return the minIdle
	 */
	public int getMinIdle() {
		return minIdle;
	}

	/**
	 * @param minIdle the minIdle to set
	 */
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	/**
	 * @return the maxIdle
	 */
	public int getMaxIdle() {
		return maxIdle;
	}

	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * @return the address
	 */
	public List<String> getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(List<String> address) {
		this.address = address;
	}

	public Integer getKeyExpiredTime() {
		return keyExpiredTime;
	}

	public void setKeyExpiredTime(Integer keyExpiredTime) {
		this.keyExpiredTime = keyExpiredTime;
	}
	
}