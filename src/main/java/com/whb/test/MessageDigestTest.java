package com.whb.test;

import org.junit.Test;

/**
 * @author whb
 * @date 2018年1月5日 下午1:51:21 
 * @Description: 
 */
public class MessageDigestTest {
	
	/*MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。

	MessageDigest 对象开始被初始化。该对象通过使用 update（）方法处理数据。任何时候都可以调用 reset（）方法重置摘要。一旦所有需要更新的数据都已经被更新了，
	应该调用digest() 方法之一完成哈希计算。

	对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。*/

	@SuppressWarnings("static-access")
	@Test
	public void test() {
		try {
		     String myinfo="我的测试信息";
		    //java.security.MessageDigest alg=java.security.MessageDigest.getInstance("MD5");
		      java.security.MessageDigest alga=java.security.MessageDigest.getInstance("SHA-1");
		      alga.update(myinfo.getBytes());
		      byte[] digesta=alga.digest();
		      System.out.println("本信息摘要是:"+byte2hex(digesta));
		      
		      String myinfoB="我的测试信息12456";
		      java.security.MessageDigest algc=java.security.MessageDigest.getInstance("SHA-1");
		      alga.update(myinfoB.getBytes());
		      byte[] digestc=algc.digest();
		      System.out.println("本信息摘要是b:"+byte2hex(digestc));
		      
		      //通过某中方式传给其他人你的信息(myinfo)和摘要(digesta) 对方可以判断是否更改或传输正常
		      java.security.MessageDigest algb=java.security.MessageDigest.getInstance("SHA-1");
		      algb.update(myinfo.getBytes());
		      if (algb.isEqual(digesta,algb.digest())) {
		         System.out.println("信息检查正常");
		       }
		       else
		        {
		          System.out.println("摘要不相同");
		         }
		   }
		   catch (java.security.NoSuchAlgorithmException ex) {
		     System.out.println("非法摘要算法");
		   }
	}
	
	public String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

}
