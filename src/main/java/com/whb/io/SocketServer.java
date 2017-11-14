package com.whb.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author whb
 * @date 2017年11月10日 下午3:24:59 
 * @Description: 
 */
public class SocketServer {

	/**
	 * 日志
	 */
//	private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

	public static void main(String[] args) throws Exception {
		
		
		ServerSocket serverSocket = new ServerSocket(83);

		try {
			while (true) {
				Socket socket = serverSocket.accept();

				// 下面我们收取信息
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				Integer sourcePort = socket.getPort();
				int maxLen = 2048;
				byte[] contextBytes = new byte[maxLen];
				// 这里也会被阻塞，直到有数据准备好
				int realLen = in.read(contextBytes, 0, maxLen);
				// 读取信息
				String message = new String(contextBytes, 0, realLen);

				// 下面打印信息
//				SocketServer.LOGGER.info("服务器收到来自于端口：" + sourcePort + "的信息：" + message);
				System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);

				// 下面开始发送信息
				out.write("回发响应信息！".getBytes());

				// 关闭
				out.close();
				in.close();
				socket.close();
			}
		} catch (Exception e) {
//			SocketServer.LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
		} finally {
			if (serverSocket != null) {
				serverSocket.close();
			}
		}
	}
}
