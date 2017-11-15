package com.whb.mq.bean;

import com.whb.mq.entity.ActiveMQMessage;

/**
 * @author whb
 * @date 2017年11月14日 下午6:31:46 
 * @Description: ActiveMQ消息体测试类，继承于ActiveMQMessage
 */
public class TestMqMessage extends ActiveMQMessage {
	
	/**@Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 3033991827428102391L;
	private String sendText;

	public String getSendText() {
		return sendText;
	}

	public void setSendText(String sendText) {
		this.sendText = sendText;
	}

}
