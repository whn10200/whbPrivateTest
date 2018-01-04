package com.whb.imageProject.pojo;

import java.io.Serializable;

import com.whb.imageProject.exception.BusinessCode;

/**
 * 处业务信息以外的描述信息部分
 * @author yinwenjie
 *
 */
public class DescPojo extends AbstractPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4081588423913750219L;

	/**
	 * 接口请求的返回代码，从这个代码中可以识别接口调用是否成功，以及调用失败时的错误类型。 具体的code定义参见技术文档
	 */
	private BusinessCode result_code;

	/**
	 * 错误的中文描述，错误信息的详细中文描述
	 */
	private String result_msg = "";

	/**
	 * 数据模式
	 */
	private String data_mode;

	/**
	 * 报文摘要
	 */
	private String digest;

	public DescPojo(String result_msg, BusinessCode result_code) {
		this.result_msg = result_msg;
		this.result_code = result_code;
		this.data_mode = "";
		this.digest = "";
	}

	public BusinessCode getResult_code() {
		return result_code;
	}

	public void setResult_code(BusinessCode result_code) {
		this.result_code = result_code;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public String getData_mode() {
		return data_mode;
	}

	public void setData_mode(String data_mode) {
		this.data_mode = data_mode;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

}
