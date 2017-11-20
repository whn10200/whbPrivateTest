package com.whb.mq.helper;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

import com.whb.mq.entity.ActiveMQConfig;

/**
 * @author whb
 * @date 2017年11月15日 上午9:49:12 
 * @Description: ActiveMQ连接池
 */
public class ActiveMQConnectionPool {
	private static final Log log = LogFactory.getLog(ActiveMQConnectionPool.class);

	private static ActiveMQConnectionFactory factory = null;
	private static CachingConnectionFactory cachingConnectionFactory = null;
	private static JmsTemplate jmsTemplate = null;
	private static ActiveMQConfig config = null;
	private static Map<String, CachingConnectionFactory> holder = new HashMap<>();

	public static void initActiveMQConnectionFactory(ActiveMQConfig config) {
		setConfig(config);

		if ((StringUtils.isEmpty(config.getUserName())) || (StringUtils.isEmpty(config.getPassword())))
			factory = new ActiveMQConnectionFactory(config.getBrokerUrl());
		else {
			factory = new ActiveMQConnectionFactory(config.getUserName(), config.getPassword(), config.getBrokerUrl());
		}
		factory.setClientIDPrefix(config.getClientIDPrefix());
		// 设置最大重发次数,实际工作中建议将这个上限值设置为3
		factory.getRedeliveryPolicy().setMaximumRedeliveries(config.getMaximumRedeliveries());
		
		cachingConnectionFactory = new CachingConnectionFactory(factory);
		cachingConnectionFactory.setSessionCacheSize(config.getSessionCacheSize());

		jmsTemplate = new JmsTemplate(cachingConnectionFactory);

		holder.put(config.getBrokerUrl(), cachingConnectionFactory);
	}

	public static void refreshUrl(ActiveMQConfig config) {
		if (config == null) {
			return;
		}
		if (factory == null) {
			initActiveMQConnectionFactory(config);
			return;
		}
		if (!StringUtils.pathEquals(config.getBrokerUrl(), factory.getBrokerURL())) {
			factory = new ActiveMQConnectionFactory(config.getUserName(), config.getPassword(), config.getBrokerUrl());
			cachingConnectionFactory.setTargetConnectionFactory(factory);
			cachingConnectionFactory.resetConnection();
		}
	}

	public static CachingConnectionFactory createActiveMQConnectionFactory(ActiveMQConfig config) {
		if (config == null) {
			return cachingConnectionFactory;
		}
		if (factory == null) {
			initActiveMQConnectionFactory(config);
			return cachingConnectionFactory;
		}
		CachingConnectionFactory cacheFactory = (CachingConnectionFactory) holder.get(config.getBrokerUrl());

		if (cacheFactory == null) {
			factory = new ActiveMQConnectionFactory(config.getUserName(), config.getPassword(), config.getBrokerUrl());
			CachingConnectionFactory newCachingConnectionFactory = new CachingConnectionFactory(factory);
			newCachingConnectionFactory.setTargetConnectionFactory(factory);
			newCachingConnectionFactory.resetConnection();
			holder.put(config.getBrokerUrl(), newCachingConnectionFactory);
			return newCachingConnectionFactory;
		}
		return cacheFactory;
	}

	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (JMSException e) {
			log.error("close connection fail");
		}
	}

	public static JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public static void setJmsTemplate(JmsTemplate jmstemplate) {
		jmsTemplate = jmstemplate;
	}

	public static CachingConnectionFactory getCachingConnectionFactory() {
		return cachingConnectionFactory;
	}

	public static void setCachingConnectionFactory(CachingConnectionFactory connectionFactory) {
		cachingConnectionFactory = connectionFactory;
	}

	public static ActiveMQConfig getConfig() {
		return config;
	}

	public static void setConfig(ActiveMQConfig activeMQConfig) {
		config = activeMQConfig;
	}
}