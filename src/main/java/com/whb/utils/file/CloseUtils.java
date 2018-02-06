package com.whb.utils.file;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author whb
 * @date 2018年2月6日 下午2:32:21 
 * @Description: 关闭流
 */
public class CloseUtils {

	private static Logger log = LoggerFactory.getLogger(CloseUtils.class);

	public static void closeStream(Closeable stream) {
		if (null != stream) {
			try {
				stream.close();
				stream = null;
			} catch (IOException e) {
				log.error("close stream exception e - >" + e);
			}
		}
	}

	public static void closeStream(Closeable... streams) {
		for (Closeable stream : streams) {
			closeStream(stream);
		}
	}
}
