package com.whb.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author whb
 * @date 2018年2月6日 下午2:32:31 
 * @Description: 关闭流
 */
public class CloseStreamUtil {
	
	private static Logger log = LoggerFactory.getLogger(CloseStreamUtil.class);
	
	
	public static void closeStream(Closeable stream){
		if(null != stream){
			try {
				stream.close();
				stream = null;
			} catch (IOException e) {
				log.error("close stream exception e - >" + e);
			}
		}
	}
	
	public static void closeStream(Closeable...streams) {
		for(Closeable stream : streams) {
			closeStream(stream);
		}
	}
}
