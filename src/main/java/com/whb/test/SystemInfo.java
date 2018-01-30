package com.whb.test;

import java.math.BigDecimal;

public class SystemInfo {

	public static void main(String[] args) {
		
		/*Map<String, String> envs = System.getenv();
		
		for (String string : envs.keySet()) {
			System.out.println(string+"====="+envs.get(string));
		}*/
		
		try {
			testBoolean();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (true && 1 == 1 && 2 == 2 && BigDecimal.ZERO.compareTo(new BigDecimal("0")) == 0) {
			System.out.println(22);
		}
		
		
	}

	private static Boolean testBoolean() throws InterruptedException {
		try {
			System.out.println(123);
			Thread.sleep(10000);
			return true;
		} finally {
			System.out.println(9999);
		}
		
	}

}
