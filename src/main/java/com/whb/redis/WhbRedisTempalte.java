package com.whb.redis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author whb
 * @date 2017年12月13日 下午3:54:47 
 * @Description: Redis工具类
 */
public class WhbRedisTempalte {
	
	static Logger logger = LoggerFactory.getLogger(WhbRedisTempalte.class);
	static ObjectMapper mapper = new ObjectMapper();
	private StringRedisTemplate client;

	public WhbRedisTempalte(StringRedisTemplate client) {
		this.client = client;
	}

	/**
	 * @说明 set Object
	 * @param key
	 * @param obj
	 * @param seconds
	 */
	public <T> void setObject(String key, Object obj, long... seconds) {
		client.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					connection.set(key.getBytes(), mapper.writeValueAsBytes(obj));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}
	
	/**
	 * @说明 get Object
	 * @param key
	 * @param clas
	 * @return
	 */
	public <T> T getObject(String key, Class<T> clas) {
		return client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] bytes = connection.get(key.getBytes());
					if (bytes != null)
						return mapper.readValue(bytes, clas);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return null;
			}
		});
	}

	/**
	 * @说明 List put one Object
	 * @param key
	 * @param obj
	 * @param seconds
	 */
	public <T> void putList(String key, Object obj, long... seconds) {
		client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					connection.rPush(key.getBytes(), mapper.writeValueAsBytes(obj));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}

	/**
	 * @说明 List put all
	 * @param key
	 * @param list
	 * @param seconds
	 */
	public <T> void putList(String key, List<?> list, long... seconds) {
		client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				byte[][] bytes = new byte[list.size()][];
				for (int i = 0; i < list.size(); i++) {
					try {
						bytes[i] = mapper.writeValueAsBytes(list.get(i));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				connection.rPush(key.getBytes(), bytes);
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}

	/**
	 * @说明 List get index
	 * @param key
	 * @param index
	 * @param clas
	 * @return
	 */
	public <T> T getList(String key, int index, Class<T> clas) {
		return client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] bytes = connection.lIndex(key.getBytes(), index);
					if (bytes != null)
						return mapper.readValue(bytes, clas);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
				return null;
			}
		});
	}

	/**
	 * @说明 List get all
	 * @param key
	 * @param clas
	 * @return
	 */
	public <T> List<T> getList(String key, Class<T> clas) {

		return client.execute(new RedisCallback<List<T>>() {
			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> bytes = connection.lRange(key.getBytes(), 0, -1);
				if (bytes == null)
					return null;
				List<T> list = new ArrayList<>();
				for (byte[] bs : bytes) {
					try {
						list.add(mapper.readValue(bs, clas));
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				return list;
			}
		});
	}

	/**
	 * @说明 Map set one
	 * @param key
	 * @param field
	 * @param obj
	 * @param seconds
	 */
	public <T> void setMap(String key, String field, Object obj, long... seconds) {
		client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					connection.hSet(key.getBytes(), field.getBytes(), mapper.writeValueAsBytes(obj));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}

	/**
	 * @说明 Map set all
	 * @param key
	 * @param map
	 * @param seconds
	 */
	public <T> void setMap(String key, Map<String, ?> map, long... seconds) {
		client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
				for (String field : map.keySet()) {
					try {
						hashes.put(field.getBytes(), mapper.writeValueAsBytes(map.get(field)));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				connection.hMSet(key.getBytes(), hashes);
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}

	/**
	 * @说明 Map get one
	 * @param key
	 * @param field
	 * @param clas
	 * @return
	 */
	public <T> T getMap(String key, String field, Class<T> clas) {
		return client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] bytes = connection.hGet(key.getBytes(), field.getBytes());
					if (bytes != null)
						return mapper.readValue(bytes, clas);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
				return null;
			}
		});
	}

	/**
	 * @说明 Map get all
	 * @param key
	 * @param clas
	 * @return
	 */
	public <T> Map<String, T> getMap(String key, Class<T> clas) {
		return client.execute(new RedisCallback<Map<String, T>>() {
			public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> bytes = connection.hGetAll(key.getBytes());
				if (bytes == null)
					return null;
				Map<String, T> map = new HashMap<String, T>();
				for (byte[] field : bytes.keySet()) {
					try {
						map.put(new String(key), mapper.readValue(bytes.get(field), clas));
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				return map;
			}
		});
	}

	/**
	 * @说明 Map delete by key
	 * @param key
	 * @param field
	 * @return
	 */
	public <T> T delMap(String key, String field) {
		return client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				connection.hDel(key.getBytes(), field.getBytes());
				return null;
			}
		});
	}

	/**
	 * @说明 Set set one
	 * @param key
	 * @param obj
	 * @param seconds
	 */
	public <T> void setSet(String key, Object obj, long... seconds) {
		client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					connection.sAdd(key.getBytes(), mapper.writeValueAsBytes(obj));
				} catch (JsonProcessingException e) {
					logger.error(e.getMessage(), e);
				}
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}

	/**
	 * @说明 Set set all
	 * @param key
	 * @param set
	 * @param seconds
	 */
	public <T> void setSet(String key, Set<?> set, long... seconds) {
		client.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> bytes = new ArrayList<byte[]>();
				for (Object obj : set) {
					try {
						bytes.add(mapper.writeValueAsBytes(obj));
					} catch (JsonProcessingException e) {
						logger.error(e.getMessage(), e);
					}
				}
				connection.sAdd(key.getBytes(), bytes.toArray(new byte[bytes.size()][]));
				if (seconds.length > 0)
					connection.expire(key.getBytes(), seconds[0]);
				return null;
			}
		});
	}

	/**
	 * @说明 Set get all
	 * @param key
	 * @param clas
	 * @return
	 */
	public <T> Set<T> getSet(String key, Class<T> clas) {
		return client.execute(new RedisCallback<Set<T>>() {
			public Set<T> doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> bytes = connection.sMembers(key.getBytes());
				if (bytes == null)
					return null;
				Set<T> set = new HashSet<T>();
				for (byte[] byt : bytes) {
					try {
						set.add(mapper.readValue(byt, clas));
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				return set;
			}
		});
	}

	/**
	 * @说明 key 是否存在
	 * @param key
	 * @return
	 */
	public Boolean exists(String key) {
		return client.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				return connection.exists(key.getBytes());
			}
		});
	}

	/**
	 * @说明 哈希表 key 中，给定域 field 是否存在。
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hexists(String key, String field) {
		return client.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				return connection.hExists(key.getBytes(), field.getBytes());
			}
		});
	}

	/**
	 * @说明 哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		return client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				byte[] bytes = connection.hGet(key.getBytes(), field.getBytes());
				if (bytes != null)
					return new String(bytes);
				return null;
			}
		});
	}

	/**
	 * @说明 哈希表 key 中，所有的域和值。
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		return client.execute(new RedisCallback<Map<String, String>>() {
			public Map<String, String> doInRedis(RedisConnection connection) {
				Map<byte[], byte[]> bytes = connection.hGetAll(key.getBytes());
				if (bytes == null)
					return null;
				Map<String, String> map = new HashMap<String, String>();
				for (byte[] key : bytes.keySet())
					map.put(new String(key), new String(bytes.get(key)));
				return map;
			}
		});
	}

	/**
	 * @说明 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hdel(String key, String field) {
		return client.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.hDel(key.getBytes(), field.getBytes());
			}
		});
	}

	/**
	 * @说明 将哈希表 key 中的域 field 的值设为 value 。
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
				return null;
			}
		});
	}

	/**
	 * @说明 返回 key 所关联的字符串值。
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				byte[] bytes = connection.get(key.getBytes());
				if (bytes == null)
					return null;
				return new String(bytes);
			}
		});
	}

	/**
	 * @说明 返回 key 所关联的byte值。
	 * @param key
	 * @return
	 */
	public byte[] getByte(String key) {
		return client.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection) {
				try {
					return connection.get(key.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				}
				return null;
			}
		});
	}

	/**
	 * @说明 将字符串值 value 关联到 key 。
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				connection.set(key.getBytes(), value.getBytes());
				return null;
			}
		});
	}

	/**
	 * @说明 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
	 * @param index
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void set(String key, String value, int seconds) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				connection.setEx(key.getBytes(), seconds, value.getBytes());
				return null;
			}
		});
	}
	
	/**  
	* @说明 key 存活时间
	* @param key
	* @param seconds 
	*/  
	public void expire(String key, long seconds) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				connection.expire(key.getBytes(), seconds);
				return null;
			}
		});
	}

	/**
	 * @说明 删除给定的一个或多个 key 。
	 * @param key
	 */
	public void del(String... keys) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				byte[][] bytes = new byte[keys.length][];
				for (int i = 0; i < keys.length; i++)
					bytes[i] = keys[i].getBytes();
				connection.del(bytes);
				return null;
			}
		});
	}

	/**
	 * @说明 返回列表 key 的长度。
	 * @param key
	 * @return
	 */
	public Long llen(String key) {
		return client.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.lLen(key.getBytes());
			}
		});
	}

	/**
	 * @说明 将一个或多个值 value 插入到列表 key 的表头
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String value) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				connection.lPush(key.getBytes(), value.getBytes());
				return null;
			}
		});
	}

	/**
	 * @说明 将一个或多个值 value 插入到列表 key 的表头
	 * @param key
	 * @param values
	 */
	public void lpush(String key, List<String> values) {
		client.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) {
				for (String value : values)
					connection.lPush(key.getBytes(), value.getBytes());
				return null;
			}
		});
	}

	/**
	 * @说明 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		return client.execute(new RedisCallback<List<String>>() {
			public List<String> doInRedis(RedisConnection connection) {
				List<byte[]> bytes = connection.lRange(key.getBytes(), start, end);
				if (bytes == null)
					return null;
				List<String> list = new ArrayList<>();
				for (byte[] lbyte : bytes)
					list.add(new String(lbyte));
				return list;
			}
		});
	}

	/**
	 * @说明 将 key 中储存的数字值增一。
	 * @param key
	 * @return
	 */
	public Long incr(String key) {
		return client.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.incr(key.getBytes());
			}
		});
	}

	/**
	 * @说明 将 key 中储存的数字值增一。
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		return client.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.decr(key.getBytes());
			}
		});
	}

	/**
	 * @说明 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long sadd(String key, String value) {
		return client.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				return connection.sAdd(key.getBytes(), value.getBytes());
			}
		});
	}

	/**
	 * @说明 返回集合 key 中的所有成员。
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		return client.execute(new RedisCallback<Set<String>>() {
			public Set<String> doInRedis(RedisConnection connection) {
				Set<byte[]> setByte = connection.sMembers(key.getBytes());
				if (setByte == null)
					return null;
				Set<String> set = new HashSet<String>();
				for (byte[] sbyte : setByte)
					set.add(new String(sbyte));
				return set;
			}
		});
	}

	/**
	 * @说明 BRPOP 是列表的阻塞式(blocking)弹出原语。
	 * @param key
	 * @param seconds
	 * @return
	 */
	public List<String> brpop(String key, int seconds) {
		return client.execute(new RedisCallback<List<String>>() {
			public List<String> doInRedis(RedisConnection connection) {
				List<byte[]> bytes = connection.bRPop(seconds, key.getBytes());
				if (bytes == null)
					return null;
				List<String> list = new ArrayList<>();
				for (byte[] lbyte : bytes)
					list.add(new String(lbyte));
				return list;
			}
		});
	}
	/**
	 * @说明 获取锁。
	 * @param key
	 * @param lockValue
	 * @return
	 */
	public Boolean setNx(String key, String lockValue) {
		return client.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				return connection.setNX(key.getBytes(),lockValue.getBytes());
			}
		});
	}
}
