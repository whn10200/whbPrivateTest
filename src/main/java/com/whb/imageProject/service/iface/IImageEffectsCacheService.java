package com.whb.imageProject.service.iface;

import com.whb.imageProject.exception.BusinessException;

/**
 * 这个服务和图片特效从缓存读取或者保存到缓存的操作有关
 * @author yinwenjie
 */
public interface IImageEffectsCacheService {
	
	/**
	 * redis中每一个K-V中能够保存的Value的最大值
	 * 目前默认为5KB
	 */
	public static final Integer PERPATCH_IMAGE_SIZE = 1024 * 50;
	
	/**
	 * 向缓存区存储一个经过image特效处理过的图片信息。
	 * @param imageURL 图片的访问路径 ——可以区别一个图片的访问路径和特效参数
	 * @param imagebytes 图片数据
	 * @throws BusinessException
	 */
	public void saveCache(String imageURL , byte[] imagebytes) throws BusinessException;
	
	/**
	 * 试图从缓存区读取一张图片信息
	 * @param imageURL 图片信息的访问路径——可以区别一个图片的访问路径和特效参数
	 * @return 
	 * @throws BusinessException 如果读取失败则抛出这个异常
	 */
	public byte[] queryCache(String imageURL) throws BusinessException;
}