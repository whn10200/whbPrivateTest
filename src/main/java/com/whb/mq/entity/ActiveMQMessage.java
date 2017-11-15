package com.whb.mq.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author whb
 * @date 2017年11月14日 下午5:58:10 
 * @Description: ActiveMQ消息体基类 
 */
public class ActiveMQMessage implements Serializable {

	/**@Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1884136586133184997L;
	
	protected String cmd;
	protected String serialNo;
	protected String moduleName;
	private String moduleIp;
	private Date sendTime;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleIp() {
		return moduleIp;
	}
	public void setModuleIp(String moduleIp) {
		this.moduleIp = moduleIp;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	

}
