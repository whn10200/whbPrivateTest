package com.whb.imageProject.service.impl.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClusterCommand;
import redis.clients.jedis.JedisClusterConnectionHandler;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSlotBasedConnectionHandler;

import java.io.Closeable;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.whb.imageProject.service.iface.redis.IBinaryJedisCluster;

/**
 * Redis Cluster 客户端操作实现，这个类参考自JedisCluster而来<br>
 * 源自JedisCluster对JedisClusterCommand的封装只能对String类型的value进行处理
 * 不能对byte[]形式的value进行处理
 * @author yinwenjie
 */
public class BinaryJedisClusterImpl implements IBinaryJedisCluster, Closeable {
	public static final short HASHSLOTS = 16384;
	private static final int DEFAULT_TIMEOUT = 2000;
	private static final int DEFAULT_MAX_REDIRECTIONS = 20;
	
	/**
	 * 日志
	 */
	private static final Log LOGGER = LogFactory.getLog(BinaryJedisClusterImpl.class);
	
	public static enum Reset {
		SOFT, HARD
	}

	private int maxRedirections;

	private JedisClusterConnectionHandler connectionHandler;

	public BinaryJedisClusterImpl(Set<HostAndPort> nodes, int timeout) {
		this(nodes, timeout, DEFAULT_MAX_REDIRECTIONS);
	}

	public BinaryJedisClusterImpl(Set<HostAndPort> nodes) {
		this(nodes, DEFAULT_TIMEOUT);
	}

	public BinaryJedisClusterImpl(Set<HostAndPort> nodes, int timeout, int maxRedirections) {
		this(nodes, timeout, maxRedirections, new GenericObjectPoolConfig());
	}

	public BinaryJedisClusterImpl(Set<HostAndPort> nodes, final GenericObjectPoolConfig poolConfig) {
		this(nodes, DEFAULT_TIMEOUT, DEFAULT_MAX_REDIRECTIONS, poolConfig);
	}

	public BinaryJedisClusterImpl(Set<HostAndPort> nodes, int timeout, final GenericObjectPoolConfig poolConfig) {
		this(nodes, timeout, DEFAULT_MAX_REDIRECTIONS, poolConfig);
	}

	public BinaryJedisClusterImpl(Set<HostAndPort> jedisClusterNode, int timeout, int maxRedirections, final GenericObjectPoolConfig poolConfig) {
		this(jedisClusterNode, timeout, timeout, maxRedirections, poolConfig);
	}

	public BinaryJedisClusterImpl(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, int maxRedirections, final GenericObjectPoolConfig poolConfig) {
		this.connectionHandler = new JedisSlotBasedConnectionHandler(jedisClusterNode, poolConfig, connectionTimeout, soTimeout);
		this.maxRedirections = maxRedirections;
	}

	@Override
	public void close() {
		if (connectionHandler != null) {
			for (JedisPool pool : connectionHandler.getNodes().values()) {
				try {
					if (pool != null) {
						pool.destroy();
					}
				} catch (Exception e) {
					LOGGER.warn(e.getMessage() , e);
				}
			}
		}
	}

	@Override
	public String set(final String key, final byte[] value) {
		return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
			@Override
			public String execute(Jedis connection) {
				return connection.set(key.getBytes(), value);
			}
		}.run(key);
	}
	
	@Override
	public String set(final String key, final byte[] value, final String nxxx, final String expx, final long time) {
		return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
			@Override
			public String execute(Jedis connection) {
				return connection.set(key.getBytes(), value, nxxx.getBytes(), expx.getBytes(), time);
			}
		}.run(key);
	}

	@Override
	public byte[] get(final String key) {
		return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
			@Override
			public byte[] execute(Jedis connection) {
				return connection.get(key.getBytes());
			}
		}.run(key);
	}
	
	@Override
	public Boolean exists(final String key) {
		return new JedisClusterCommand<Boolean>(connectionHandler, maxRedirections) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.exists(key);
			}
		}.run(key);
	}

	@Override
	public Long persist(final String key) {
		return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
			@Override
			public Long execute(Jedis connection) {
				return connection.persist(key);
			}
		}.run(key);
	}

	@Override
	public String type(final String key) {
		return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
			@Override
			public String execute(Jedis connection) {
				return connection.type(key);
			}
		}.run(key);
	}

	@Override
	public Long expire(final String key, final int seconds) {
		return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
			@Override
			public Long execute(Jedis connection) {
				return connection.expire(key, seconds);
			}
		}.run(key);
	}
}
