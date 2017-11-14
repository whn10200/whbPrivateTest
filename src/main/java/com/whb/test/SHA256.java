package com.whb.test;

import java.security.MessageDigest;
import java.util.Base64;

public class SHA256 {
	private static final String ByteCode = "UTF-8";
	
	public static String encrypt(String key){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes(ByteCode));
			return Base64.getEncoder().encodeToString(encodeHex(digest.digest()).getBytes(ByteCode));
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	public static final String encodeHex(byte abyte0[]){
        StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
        for(int i = 0; i < abyte0.length; i++){
            if((abyte0[i] & 0xff) < 16)
                stringbuffer.append("0");
            stringbuffer.append(Long.toString(abyte0[i] & 0xff, 16));
        }
        return stringbuffer.toString();
    }
	
	public static void main(String[] args) {
		System.out.println(encrypt("123456"));
	}
	
}
