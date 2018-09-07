package com.whb;

import java.io.*;
import java.net.*;

public class DownloadPdf {

	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadByUrl(String urlStr, String fileName, String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(5 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] getData = readInputStream(inputStream);
		// 文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		System.out.println("info:" + url + " download success");

	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	public static void main(String[] args) {
		try {
			downLoadByUrl(
					"https://openapi.bestsign.info/openapi/v2/contract/download?developerId=1957022459383251560&rtick=15350926505120&signType=rsa&sign=cFzOn2HZSh1Qf2yhtppD9ErgIXfKzAp4b%2BNDsZ29idK2Vv3atAzpvaKkgoCP2nB71qhbpfq51rmCRDMBlPVzY1KCAenhP7hWOnLBb7qIZkL9FEu6ECLtsNi7fnI3wJGmHJSO%2BjzTqu2sw225u4QwVdR7bmq6cB3794SHSlS0W3U%3D&contractId=153507787101000001",
					"ELISA.pdf", "E:/upload");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
