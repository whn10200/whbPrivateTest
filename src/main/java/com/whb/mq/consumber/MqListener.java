package com.whb.mq.consumber;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.whb.mq.bean.TestMqMessage;
import com.whb.mq.constants.MessageEnum;
import com.whb.mq.helper.ActiveMqHelper;

/**
 * @author whb
 * @date 2017年11月15日 上午10:48:14 
 * @Description: ActiveMQ消息监听服务
 */
@Service
public class MqListener implements SessionAwareMessageListener<TextMessage> {
	/*
	 * 注入cache工程中的工具类：redisUtils 
	 */
//	@Resource(name ="redisUtils")
//	RedisUtils redisUtils;
	
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		//消息订阅
		ActiveMqHelper.receiveFromQueue(MessageEnum.QUEUE_TEST.value(), this, true);
	}
	@Override
	public void onMessage(TextMessage tm, Session session) throws JMSException {
		String reqText = null;
//		double y;
		try {
			reqText = tm.getText();
			TestMqMessage message = JSONObject.parseObject(reqText, TestMqMessage.class);
			if (Optional.fromNullable(message).isPresent()) {
				System.out.println("mq消费信息：" + message.getSendText() + "-----------------------------------------");
				//下面是redis工具调用示例：
//				List<String> list = new ArrayList<String>() {{add("1");add("2");add("3");}};
				try{
					//以json格式，将对象存储于redis
//					redisUtils.setJson(message.getSentText(), message, 120);
//					//获取缓存json，并反序列化为对象
//					TestMqMessage one = redisUtils.getJson(message.getSentText(), TestMqMessage.class);
//					//递增
//					redisUtils.incr("yanghanxiong-junit-TEST", 1);
//					redisUtils.incr("yanghanxiong-junit-TEST", 1);
//					//递减
//					redisUtils.decr("yanghanxiong-junit-TEST", 1);
//					//删除
//			        redisUtils.del("yanghanxiong-junit");
				}catch(Exception e){
				}finally{
					//设置list
//					redisUtils.setList("yanghanxiong-junit", list);
//			        List<String> list1= redisUtils.getList("yanghanxiong-junit");
//			        System.out.println("redis工具测试：" + list1.get(0) + "-----------------------------------------");
				}
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
		}
	}
}
