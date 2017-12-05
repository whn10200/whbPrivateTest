package com.whb.kafka.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author whb
 * @date 2017年11月22日 下午6:07:43 
 * @Description: 拦截标识注解。使用这个注解的方法说明需要向 事件/日志服务发送消息
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

	 /**
     * 您可以根据自己的需要，在这个注解中加入各种属性
     * @return
     */
    public String message() default "";
    
}
