package com.whb.imageProject.exception;

/**
 * 业务处理异常。这个异常负责在大多数场景中告知上层调用者处理过程中出现了问题。
 * 其中该异常中有一个BusinessCode枚举，描述了具体的处理问题
 * @author yinwenjie
 *
 */
public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -592173718294268531L;
	
	/**
	 * 异常响应编码 
	 */
	private BusinessCode businessCode;
	
	/**
	 * @param mess
	 * @param responseCode
	 */
	public BusinessException(String mess , BusinessCode businessCode) {
		super(mess);
		this.businessCode = businessCode;
	}

	public BusinessCode getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(BusinessCode businessCode) {
		this.businessCode = businessCode;
	}
}