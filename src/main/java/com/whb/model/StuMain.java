package com.whb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StuMain {

	public static void main(String[] args) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		System.out.println(map.put("123", "123"));
		System.out.println(map.put("123", "456"));
		System.out.println(map.get("123")); 
		
		List<Future<Boolean>> results = new ArrayList<Future<Boolean>>();
		ExecutorService es = Executors.newCachedThreadPool();
		
		Callable<Boolean> cal = () ->createCall(); 
		
		results.add(es.submit(cal));

	}

	private static Boolean createCall() {
		// TODO Auto-generated method stub
		return Boolean.FALSE;
	}

}
