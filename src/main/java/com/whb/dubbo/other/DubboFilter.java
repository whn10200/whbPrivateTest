package com.whb.dubbo.other;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.whb.redis.WhbRedisTempalte;

/**
 * @author whb
 * @date 2018年3月17日 上午11:02:18 
 * @Description: Dubbo 的名单过滤       <dubbo:provider filter="xxxFilter" />  
 */
public class DubboFilter implements Filter{
	
	static Logger logger = LoggerFactory.getLogger(DubboFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		 if (true) {  
	           System.out.println("白名单禁用");  
	            return invoker.invoke(invocation);  
	        }  

	        String clientIp = RpcContext.getContext().getRemoteHost();  
	        logger.debug("访问ip为{}", clientIp);  
	        /*List<String> allowedIps = ipWhiteList.getAllowedIps();  
	        if (allowedIps.contains(clientIp)) {  
	            return invoker.invoke(invocation);  
	        } else {  
	            return new RpcResult();  
	        }  */
	        return null;
	    }  

}
