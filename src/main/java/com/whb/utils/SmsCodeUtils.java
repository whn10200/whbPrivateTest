package com.whb.utils;

import java.util.Random;

public class SmsCodeUtils{

	private static int sms_length = 6;
	
	public static String getSmsCode() {
		return getRandNum(sms_length);
	}
	
	public static String getRandNum(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
	
	
	public static void main(String[] args) {
		System.out.println(getSmsCode());
	}
}