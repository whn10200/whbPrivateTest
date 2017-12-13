package com.whb.test;

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
		
		
	}

	private static Boolean testBoolean() throws InterruptedException {
		try {
			System.out.println(123);
			Thread.sleep(5000);
			return true;
		} finally {
			System.out.println(9999);
		}
		
	}

}
