package com.whb.imageProject.service;

import java.io.Serializable;

/**
 * 核心框架基础服务层。可选用
 * @author yinwenjie
 */
public interface ISystemService<T> {
	/**
	 * 向服务层插入一个实体对象
	 * @param entity 实体信息
	 */
	void insert(T entity);
	
	/**
	 * 向服务层传入带有唯一标示的对象，进行修改
	 * @param entity 实体信息
	 */
	void update(T entity);
	
	/**
	 * 向数据层传入T实体，数据层将根据实体主键信息决定是更新数据库还是向数据库写入新数据
	 * @param entity 实体信息
	 */
	void saveOrUpdate(T entity);
	
	/**
	 * 向服务层插入一个实体对象,并通知数据层马上执行这个更新
	 * @param entity 实体信息
	 */
	void refresh(T entity);
	
	/**
	 * 在服务层使用一个可以被串行化的唯一标示，找到相应的数据对象
	 * @param id 序列id
	 */
	T getEntity(Serializable id);
	
	/**
	 * 在服务层删除指定的唯一标示的数据
	 * @param id
	 */
	void delete(Serializable id);
}
