package com.whb.rpc.server;

import java.io.IOException;

/**
 * @author whb
 * @date 2017年12月7日 下午8:46:49 
 * @Description: 服务注册中心
 */
public interface Server {

	public void stop();  
	  
	  
    public void start() throws IOException;  
  
  
    public void register(Class serviceInterface, Class impl);  
  
  
    public boolean isRunning();  
  
  
    public int getPort();  
}
