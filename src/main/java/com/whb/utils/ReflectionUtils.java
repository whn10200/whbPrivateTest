package com.whb.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;
import com.whb.model.Student;

/**
 * @author whb
 * @date 2018年2月6日 下午3:52:03 
 * @Description: 利用反射，将实体转成Map
 */
public class ReflectionUtils {
	
	public static Map<String, Object> convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map<String, Object> returnMap = Maps.newHashMap();
		if(null == bean) return returnMap;
		Class<?> type = bean.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			boolean equals = descriptor.getPropertyType().equals(Integer.class);
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					if(equals) {
						returnMap.put(propertyName, 0);
					}else {
						returnMap.put(propertyName, "");
					}
					
				}
			}
		}
		return returnMap;
	}
	
	public static void main(String[] args) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		
		Map<String, Object> returnMap = ReflectionUtils.convertBean(new Student());
		System.out.println(returnMap.toString());
		
	}
}
