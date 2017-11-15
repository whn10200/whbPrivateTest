package com.whb.mq.constants;
/**
 * @author whb
 * @date 2017年11月14日 下午6:33:50 
 * @Description: 枚举类：ActiveMQ消息主题
 */
public enum MessageEnum {
	
	/**
	 * 消息主题：队列_测试
	 */
	QUEUE_TEST( MqConstant.PREFIX_YWXT  + "QUEUE_TEST");
	
	private final String value;

	private MessageEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}