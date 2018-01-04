package com.whb.imageProject.service.impl;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whb.imageProject.configuration.RedisProperties;
import com.whb.imageProject.exception.BusinessCode;
import com.whb.imageProject.exception.BusinessException;
import com.whb.imageProject.service.iface.IImageEffectsCacheService;
import com.whb.imageProject.service.iface.redis.IBinaryJedisCluster;

/**
 * 对数据进行缓存操作的具体实现
 * @author yinwenjie
 */
@Service("ImageEffectsCacheServiceImpl")
public class ImageEffectsCacheServiceImpl implements IImageEffectsCacheService {
	
	/**
	 * redis cluster的客户端，基于byte进行操作
	 */
	@Autowired
	private IBinaryJedisCluster binaryJedisCluster;
	
	@Autowired
	private RedisProperties redisProperties;
	
	/* (non-Javadoc)
	 * @see com.whb.imageProject.sample.image.service.iface.IImageEffectsCacheService#saveCache(java.lang.String, byte[])
	 */
	@Override
	public void saveCache(String imageURL, byte[] imagebytes) throws BusinessException {
		/*
		 * 1、经过合法性验证后，处理的第一步就是判断imagebytes需要被分成几个段
		 * 2、然后进行byte的拆分和保存
		 * 3、只有所有的段都保存成功了，才进行返回（同步的，连续性的）
		 * */
		if(StringUtils.isEmpty(imageURL) || imagebytes == null) {
			throw new BusinessException("参数错误，请检查！", BusinessCode._404);
		}
		
		// 1、======确定分段
		Integer maxLen = imagebytes.length;
		Integer splitNum = maxLen / IImageEffectsCacheService.PERPATCH_IMAGE_SIZE;
		Integer remainLen = maxLen % IImageEffectsCacheService.PERPATCH_IMAGE_SIZE;
		if(remainLen != 0) {
			splitNum++;
		}
		// 开始构造key
		String key[] = new String[splitNum];
		for(int index = 0 ; index < splitNum ; index++) {
			key[index] = imageURL + "|" + index;
		}
		String lenkey = imageURL + "|size";
		
		// 2、======拆分value并保存
		// 保存长度信息到缓存系统
		binaryJedisCluster.set(lenkey, maxLen.toString().getBytes());
		for(int index = 0 ; index < splitNum ; index++) {
			byte[] values = null;
			Integer valuesLen = null;
			// 确定本次要添加的分片长度
			if(index + 1 == splitNum) {
				valuesLen = remainLen == 0?IImageEffectsCacheService.PERPATCH_IMAGE_SIZE:remainLen;
			} else {
				valuesLen = IImageEffectsCacheService.PERPATCH_IMAGE_SIZE;
			}
			values = new byte[valuesLen];
			
			// 复制byte信息
			// System.arraycopy是操作系统级别的复制操作，比arraybyte stream快
			System.arraycopy(imagebytes, index * IImageEffectsCacheService.PERPATCH_IMAGE_SIZE, values, 0, valuesLen);
			binaryJedisCluster.set(key[index], values , "NX" , "EX" , redisProperties.getKeyExpiredTime());
		}
	}

	/* (non-Javadoc)
	 * @see com.whb.imageProject.sample.image.service.iface.IImageEffectsCacheService#queryCache(java.lang.String)
	 */
	@Override
	public byte[] queryCache(String imageURL) throws BusinessException {
		/*
		 * 1、经过合法性验证后，处理的第一步就是取得当前imageURL的大小信息
		 * 2、再根据这个大小还原分片信息
		 * 3、访问分片信息，并重新拼凑整个image的数据信息
		 * 
		 * 注意，以上不管任何一步出现错误，都视为从缓存中读取数据失败
		 * */
		if(StringUtils.isEmpty(imageURL)) {
			throw new BusinessException("参数错误，请检查！", BusinessCode._404);
		}
		
		// 1、=======
		String lenkey = imageURL + "|size";
		byte[] lenByte = binaryJedisCluster.get(lenkey);
		if(lenByte == null) {
			return null;
		}
		Integer maxLen = Integer.valueOf(new String(lenByte));
		Integer splitNum = maxLen / IImageEffectsCacheService.PERPATCH_IMAGE_SIZE;
		Integer remainLen = maxLen % IImageEffectsCacheService.PERPATCH_IMAGE_SIZE;
		if(remainLen != 0) {
			splitNum++;
		}
		
		// 2、=======
		// 开始构造key
		Map<Integer, byte[]> splitBytesTree = new TreeMap<Integer, byte[]>();
		for(int index = 0 ; index < splitNum ; index++) {
			String key = imageURL + "|" + index;
			byte[] valuesBytes = binaryJedisCluster.get(key);
			// 如果有一个分片失败，就认为整个读取过程失败了 
			if(valuesBytes == null) {
				return null;
			}
			splitBytesTree.put(index, valuesBytes);
		}
		
		// 3、======开始合并
		byte[] resultsBytes = new byte[maxLen];
		for(int index = 0 ; index < splitNum ; index++) {
			byte[] splitBytes = splitBytesTree.get(index);
			System.arraycopy(splitBytes, 0, resultsBytes, index * IImageEffectsCacheService.PERPATCH_IMAGE_SIZE, splitBytes.length);
		}
		
		return resultsBytes;
	}
}