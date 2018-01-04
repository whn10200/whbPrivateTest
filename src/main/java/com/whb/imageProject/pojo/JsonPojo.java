package com.whb.imageProject.pojo;

import java.io.Serializable;

import com.whb.imageProject.exception.BusinessCode;

/**
 * 该数据体用于进行http协议json格式接口的返回信息描述。
 * 
 * @author yinwenjie
 * 
 */
public class JsonPojo extends AbstractPojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2456254862958624358L;
	
	/**
	 * 返回的请求结果查询
	 */
	private Object data;

	private DescPojo desc;

	public JsonPojo(Object data, String result_msg, BusinessCode result_code) {
		this.data = data;
		this.desc = new DescPojo(result_msg, result_code);
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	public DescPojo getDesc() {
		return desc;
	}

	public void setDesc(DescPojo desc) {
		this.desc = desc;
	}
}
