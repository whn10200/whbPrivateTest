package com.whb.mq.entity;

import javax.jms.DeliveryMode;
import javax.jms.Message;

import org.springframework.boot.autoconfigure.jms.JmsProperties.AcknowledgeMode;
import org.springframework.jms.listener.AbstractPollingMessageListenerContainer;

/**
 * @author whb
 * @date 2017年11月14日 下午6:35:03 
 * @Description: ActiveMQ消息配置
 */
public class ActiveMQConfig {
	
	/**
	 * 一般默认是10个
	 */
	private int sessionCacheSize = 20;

	/**
	 * 默认采用openview的协议，其实就是TCP进行协议
	 * 相关设置在ActiveMQ安装目录的./conf/conf/activemq.xml主配置文件中
	 * <transportConnectors>
     * 		<transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
     * 		<transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
     * 		<transportConnector name="stomp" uri="stomp://0.0.0.0:61613"/>
     * 		<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
     * 		<transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
     * </transportConnectors>
	 */
	private String brokerUrl = "tcp://localhost:61616";

	/**
	 * 队列名称
	 */
	private String subject = "test";
	
	private String userName = null;
    
	private String password = null;

	/**
	 * 是否支持事务模式
	 */
	private boolean transcationEnabled = false;

	/**
	 * 持久化
	 */
	private int deliveryMode = DeliveryMode.PERSISTENT;
	/**
	 * 设置消息自动应答
	 */
	private int acknowledgeMode = AcknowledgeMode.AUTO.getMode();

	/**
	 * 非pub/sub模型（发布/订阅），即：队列模型
	 */
	private boolean pubSubDomain = false;

	private String clientIDPrefix = "tuodao";

	/**
	 * 是否持久化订阅
	 */
	private boolean durableSubEnabled = false;

	private String clientID = "tuodao_amq_demo";

	private String durableSubscriptionName = null;
	
	/**
	 * 消息优先级（0最低，9最高。0-4作为一般的优先级，5-9作为加速优先级）
	 */
	private int priority = Message.DEFAULT_PRIORITY;
	
	/**
	 * 接收等待时间
	 */
	private long receiveTimeout = AbstractPollingMessageListenerContainer.DEFAULT_RECEIVE_TIMEOUT;
	/**
	 * 设置最大重发次数,一般默认是6次，实际工作中建议将这个上限值设置为3
	 */
	private int maximumRedeliveries = 3;

	public int getSessionCacheSize() {
		return this.sessionCacheSize;
	}

	public void setSessionCacheSize(int sessionCacheSize) {
		this.sessionCacheSize = sessionCacheSize;
	}

	public String getBrokerUrl() {
		return this.brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isTranscationEnabled() {
		return this.transcationEnabled;
	}

	public void setTranscationEnabled(boolean transcationEnabled) {
		this.transcationEnabled = transcationEnabled;
	}

	public int getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public int getAcknowledgeMode() {
		return this.acknowledgeMode;
	}

	public void setAcknowledgeMode(int acknowledgeMode) {
		this.acknowledgeMode = acknowledgeMode;
	}

	public boolean isPubSubDomain() {
		return this.pubSubDomain;
	}

	public void setPubSubDomain(boolean pubSubDomain) {
		this.pubSubDomain = pubSubDomain;
	}

	public String getClientIDPrefix() {
		return this.clientIDPrefix;
	}

	public void setClientIDPrefix(String clientIDPrefix) {
		this.clientIDPrefix = clientIDPrefix;
	}

	public boolean isDurableSubEnabled() {
		return this.durableSubEnabled;
	}

	public void setDurableSubEnabled(boolean durableSubEnabled) {
		this.durableSubEnabled = durableSubEnabled;
	}

	public String getClientID() {
		return this.clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getDurableSubscriptionName() {
		return this.durableSubscriptionName;
	}

	public void setDurableSubscriptionName(String durableSubscriptionName) {
		this.durableSubscriptionName = durableSubscriptionName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getReceiveTimeout() {
		return receiveTimeout;
	}

	public void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}

	public int getMaximumRedeliveries() {
		return maximumRedeliveries;
	}

	public void setMaximumRedeliveries(int maximumRedeliveries) {
		this.maximumRedeliveries = maximumRedeliveries;
	}
}