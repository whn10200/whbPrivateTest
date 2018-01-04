package com.whb.imageProject.pojo;

/**
 * 抽象业务对象
 * @author yinwenjie
 */
public abstract class AbstractPojo {
	/**
	 * 业务数据编号
	 */
	protected String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}