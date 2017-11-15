package com.whb.mq.helper;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.whb.mq.entity.ActiveMQConfig;
import com.whb.mq.entity.ActiveMQMessage;
import com.whb.mq.util.CommonUtil;
import com.whb.mq.util.IpUtil;

/**
 * @author whb
 * @date 2017年11月15日 上午10:06:51 
 * @Description: ActiveMQ消息工具类 使用线程池完成信息任务
 */
public class ActiveMqHelper {
	private static final Logger log = LoggerFactory.getLogger(ActiveMqHelper.class);
	private static JmsTemplate jmsTemplate;
	private static String brokerUrl;
	private static String userName;
	private static String password;
	private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
	
	static {
		try {
			InputStream inCfg = ActiveMqHelper.class.getClassLoader().getResourceAsStream("amq.properties");
			Properties prop = new Properties();
			prop.load(inCfg);
			brokerUrl = prop.getProperty("mq.brokerUrl");
			userName = prop.getProperty("mq.userName");
			password = prop.getProperty("mq.password");
			log.info("-------AMQ init success...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendToQueue(ActiveMQMessage message) {
		log.info("-------sendToQueue...");
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		send(config, message);
	}

	public static void sendToQueueSync(ActiveMQMessage message) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		sendSync(config, message);
	}

	public static void sendToTopic(ActiveMQMessage message) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		send(config, message);
	}

	public static void sendToTopicSync(ActiveMQMessage message) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		sendSync(config, message);
	}

	public static void sendToQueue(ActiveMQMessage message, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		send(config, message);
	}

	public static void sendToQueueSync(ActiveMQMessage message, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		sendSync(config, message);
	}

	public static void sendToTopic(ActiveMQMessage message, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		send(config, message);
	}

	public static void sendToTopicSync(ActiveMQMessage message, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		sendSync(config, message);
	}

	public static void sendToQueue(ActiveMQMessage message, boolean transcationEnabled, int deliveryMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		config.setDeliveryMode(deliveryMode);
		send(config, message);
	}

	public static void sendToTopic(ActiveMQMessage message, boolean transcationEnabled, int deliveryMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		config.setDeliveryMode(deliveryMode);
		send(config, message);
	}

	public static void sendToQueue(ActiveMQMessage message, boolean transcationEnabled, int deliveryMode,
			int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		config.setDeliveryMode(deliveryMode);
		config.setAcknowledgeMode(acknowledgeMode);
		send(config, message);
	}

	public static void sendToQueueSync(ActiveMQMessage message, boolean transcationEnabled, int deliveryMode,
			int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		config.setDeliveryMode(deliveryMode);
		config.setAcknowledgeMode(acknowledgeMode);
		sendSync(config, message);
	}

	public static void sendToTopic(ActiveMQMessage message, boolean transcationEnabled, int deliveryMode,
			int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		config.setDeliveryMode(deliveryMode);
		config.setAcknowledgeMode(acknowledgeMode);
		send(config, message);
	}

	public static void sendToTopicSync(ActiveMQMessage message, boolean transcationEnabled, int deliveryMode,
			int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		config.setDeliveryMode(deliveryMode);
		config.setAcknowledgeMode(acknowledgeMode);
		sendSync(config, message);
	}
	/**
	 * 消息异步发送
	 * @param config 配置文件
	 * @param message 消息体
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void send(ActiveMQConfig config, ActiveMQMessage message) {
		try {
			final ActiveMQMessage sendMessage = messageExtra(message);
			ActiveMQConfig sendConfig = getMQUrl(config, message);
			ActiveMQConnectionPool.refreshUrl(sendConfig);
			jmsTemplate = ActiveMQConnectionPool.getJmsTemplate();
			ListenableFuture<?> explosion = service.submit(new Callable() {
				public String call() throws Exception {
					ActiveMqHelper.jmsTemplate.setPubSubDomain(sendConfig.isPubSubDomain());

					if (DeliveryMode.PERSISTENT == sendConfig.getDeliveryMode()) {
						//如果不打开服务质量的开关，消息的递送模式、优先级和存活时间的设置就没有作用。
						//发送消息的时候，是否使用QOS的值,如果是true，deliveryMode, priority, 和 timeToLive的值将被使用；否则使用默认的值。
						ActiveMqHelper.jmsTemplate.setExplicitQosEnabled(true);
					}
					
					ActiveMqHelper.jmsTemplate.setDeliveryMode(sendConfig.getDeliveryMode());
					ActiveMqHelper.jmsTemplate.setSessionTransacted(sendConfig.isTranscationEnabled());
					ActiveMqHelper.jmsTemplate.setSessionAcknowledgeMode(sendConfig.getAcknowledgeMode());
					ActiveMqHelper.jmsTemplate.setPriority(sendConfig.getPriority());

					ActiveMqHelper.jmsTemplate.send(sendConfig.getSubject(), new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							TextMessage textMessage = session.createTextMessage();
							String text = JSON.toJSONString(sendMessage);
							textMessage.setText(text);
							ActiveMqHelper.log.info("sendToMQ:{}", text);
							return textMessage;
						}
					});
					return null;
				}
			});
			Futures.addCallback(explosion, new FutureCallback() {
				@Override
				public void onSuccess(Object result) {
				}
				
				@Override
				public void onFailure(Throwable t) {
					ActiveMqHelper.log.error("----存入消息队列失败----" + t.getMessage() + "--args:" + JSON.toJSONString(sendMessage));
				}
			});
		} catch (Exception e) {
			log.error("----存入消息队列失败----" + e.getMessage() + "--args:" + JSON.toJSONString(message));
		}
	}

	/**
	 * 消息同步发送
	 * @param config 配置文件
	 * @param message 消息体
	 */
	private static void sendSync(ActiveMQConfig config, ActiveMQMessage message) {
		ActiveMQMessage sendMessage = messageExtra(message);
		ActiveMQConfig sendConfig = getMQUrl(config, message);
		ActiveMQConnectionPool.refreshUrl(sendConfig);

		jmsTemplate = ActiveMQConnectionPool.getJmsTemplate();
		jmsTemplate.setPubSubDomain(sendConfig.isPubSubDomain());

		if (DeliveryMode.PERSISTENT == sendConfig.getDeliveryMode()) {
			jmsTemplate.setExplicitQosEnabled(true);
		}
		jmsTemplate.setDeliveryMode(sendConfig.getDeliveryMode());
		jmsTemplate.setSessionTransacted(sendConfig.isTranscationEnabled());
		jmsTemplate.setSessionAcknowledgeMode(sendConfig.getAcknowledgeMode());
		jmsTemplate.setPriority(sendConfig.getPriority());

		jmsTemplate.send(sendConfig.getSubject(), new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				String text = JSON.toJSONString(sendMessage);
				textMessage.setText(text);
				ActiveMqHelper.log.info("sendToMQ:{}", text);
				return textMessage;
			}
		});
	}

	public static void receiveFromQueue(String subject, Object messageListener) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(false);
		receive(config, messageListener);
	}

	public static void receiveFromTopic(String subject, Object messageListener) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		receive(config, messageListener);
	}

	public static void receiveFromQueue(String subject, Object messageListener, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		receive(config, messageListener);
	}

	public static void receiveFromTopic(String subject, Object messageListener, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		receive(config, messageListener);
	}

	public static void receiveFromQueue(String subject, Object messageListener, boolean transcationEnabled,
			int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(false);
		config.setTranscationEnabled(transcationEnabled);
		config.setAcknowledgeMode(acknowledgeMode);
		receive(config, messageListener);
	}

	public static void receiveFromTopic(String subject, Object messageListener, boolean transcationEnabled,
			int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setTranscationEnabled(transcationEnabled);
		config.setAcknowledgeMode(acknowledgeMode);
		receive(config, messageListener);
	}

	public static void receiveFromDurableTopic(String subject, Object messageListener, String moduleName) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setDurableSubEnabled(true);
		config.setClientID(moduleName + "_" + IpUtil.getStringIp());
		config.setDurableSubscriptionName(moduleName + "_" + subject);
		receive(config, messageListener);
	}

	public static void receiveFromDurableTopic(String subject, Object messageListener, String moduleName,
			boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setDurableSubEnabled(true);
		config.setClientID(moduleName + "_" + IpUtil.getStringIp());
		config.setDurableSubscriptionName(moduleName + "_" + subject);
		config.setTranscationEnabled(transcationEnabled);
		receive(config, messageListener);
	}

	public static void receiveFromDurableTopic(String subject, Object messageListener, String moduleName,
			String durableSubscriptionName) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setDurableSubEnabled(true);
		config.setClientID(moduleName + "_" + IpUtil.getStringIp());
		config.setDurableSubscriptionName(durableSubscriptionName);
		receive(config, messageListener);
	}

	public static void receiveFromDurableTopic(String subject, Object messageListener, String moduleName,
			String durableSubscriptionName, boolean transcationEnabled) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setDurableSubEnabled(true);
		config.setClientID(moduleName + "_" + IpUtil.getStringIp());
		config.setDurableSubscriptionName(durableSubscriptionName);
		config.setTranscationEnabled(transcationEnabled);
		receive(config, messageListener);
	}

	public static void receiveFromDurableTopic(String subject, Object messageListener, String moduleName,
			String durableSubscriptionName, boolean transcationEnabled, int acknowledgeMode) {
		ActiveMQConfig config = new ActiveMQConfig();
		config.setSubject(subject);
		config.setPubSubDomain(true);
		config.setDurableSubEnabled(true);
		config.setClientID(moduleName + "_" + IpUtil.getStringIp());
		config.setDurableSubscriptionName(durableSubscriptionName);
		config.setTranscationEnabled(transcationEnabled);
		config.setAcknowledgeMode(acknowledgeMode);
		receive(config, messageListener);
	}

	private static void receive(ActiveMQConfig config, Object messageListener) {
		try {
			config = getMQUrl(config, null);
			CachingConnectionFactory cachingConnectionFactory = ActiveMQConnectionPool
					.createActiveMQConnectionFactory(config);

			DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();

			listenerContainer.setDestinationName(config.getSubject());

			listenerContainer.setMessageListener(messageListener);

			listenerContainer.setPubSubDomain(config.isPubSubDomain());

			listenerContainer.setSessionAcknowledgeMode(config.getAcknowledgeMode());

			listenerContainer.setSessionTransacted(config.isTranscationEnabled());

			listenerContainer.setReceiveTimeout(config.getReceiveTimeout());
			
			if (config.isDurableSubEnabled()) {
				listenerContainer.setSubscriptionDurable(config.isDurableSubEnabled());

				String oldClientID = cachingConnectionFactory.createConnection().getClientID();
				if (!config.getClientID().equals(oldClientID)) {
					cachingConnectionFactory.setClientId(config.getClientID());
					cachingConnectionFactory.resetConnection();
				}

				listenerContainer.setClientId(config.getClientID());

				listenerContainer.setDurableSubscriptionName(config.getDurableSubscriptionName());
			}

			listenerContainer.setConnectionFactory(cachingConnectionFactory);

			listenerContainer.initialize();

			listenerContainer.start();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("-----创建消息监听器失败-----" + e.getLocalizedMessage());
		}
	}

	private static ActiveMQConfig getMQUrl(ActiveMQConfig config, ActiveMQMessage message) {
		String cmd = "";

		if (message == null)
			cmd = config.getSubject();
		else {
			cmd = message.getCmd();
		}

		config.setBrokerUrl(brokerUrl);
		config.setUserName(userName);
		config.setPassword(password);

		config.setSubject(cmd);

		return config;
	}

	private static ActiveMQMessage messageExtra(ActiveMQMessage message) {
		message.setModuleIp(IpUtil.getLocalIP());
		message.setSendTime(new Date());
		message.setSerialNo(CommonUtil.getSerialNo());
		return message;
	}
}