package com.whb.dataSecurity;

import java.security.MessageDigest;

public class MD5 {
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		origin = origin.trim();
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
		} catch (Exception localException) {
		}
		return resultString;
	}

	public static String MD5Encode(byte[] bytes) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(bytes));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public static String MD5Encode(String origin, String charsetName) {
		origin = origin.trim();
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
		} catch (Exception localException) {
		}
		return resultString;
	}

	public static void main(String[] args) {
		String s = "BF05CFC4B1475CA9DB8D5FE51F326EEB".toLowerCase();
		System.out.println(s);
		String sr = MD5Encode(
				"13910000003|15020616252200001920|4563517501013883117|钟凌霜|0|430221199008282318|13910000003|" + s);
		System.out.println(sr);
	}
}
