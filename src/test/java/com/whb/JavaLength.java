package com.whb;

import java.io.UnsupportedEncodingException;

public class JavaLength {

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 中文常见字
		String s = "你好";
		System.out.println("1. string length =" + s.length());
		System.out.println("1. string bytes length =" + s.getBytes().length);
		System.out.println("1. string bytes length =" + s.getBytes("ISO-8859-1").length);
		System.out.println("1. string char length =" + s.toCharArray().length);
		System.out.println();
		// emojis
		s = "👦👩";
		System.out.println("2. string length =" + s.length());
		System.out.println("2. string bytes length =" + s.getBytes().length);
		System.out.println("2. string char length =" + s.toCharArray().length);
		System.out.println();
		// 中文生僻字
		s = "𡃁妹";
		System.out.println("3. string length =" + s.length());
		System.out.println("3. string bytes length =" + s.getBytes().length);
		System.out.println("3. string char length =" + s.toCharArray().length);
		System.out.println();

	}

}
