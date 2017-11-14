package com.whb.test;

import java.util.Map;

public class SystemInfo {

	public static void main(String[] args) {
		
		Map<String, String> envs = System.getenv();
		
		for (String string : envs.keySet()) {
			System.out.println(string+"====="+envs.get(string));
		}

	}

}
