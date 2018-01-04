package com.whb.imageProject.service.iface.redis;

/**
 * 这个redis cluster客户端，用于进行byte[] value操作
 * @author yinwenjie
 */
public interface IBinaryJedisCluster {
	/**
	 * @param key
	 * @param Byte
	 * @Returns Status code reply
	 */
	public String set(final String key, final byte[] values);
	
	/**
	 * @param key 
	 * @param value 
	 * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
	 * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
	 * @param time expire time in the units of expx
	 */
	public String set(final String key, final byte[] value, final String nxxx, final String expx, final long time);

	/**
	 * @param key
	 * @return
	 */
	public byte[] get(final String key);

	/**
	 * @param key
	 * @return
	 */
	public Boolean exists(final String key);

	/**
	 * @param key
	 * @return
	 */
	public Long persist(final String key);

	/**
	 * @param key
	 * @return
	 */
	public String type(final String key);

	/**
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(final String key, final int seconds);
}
