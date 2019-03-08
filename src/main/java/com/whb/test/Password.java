package com.whb.test;

import java.util.LinkedList;
import java.util.List;

public class Password {

	public static void main(String[] args) {
		List<String> phoneList = new LinkedList<String>();
		List<String> salesmanIdList = new LinkedList<String>();
		
		//setInfo(phoneList,salesmanIdList);

		phoneList.add("009762");salesmanIdList.add("139713");
		
		for (int i = 0; i < phoneList.size(); i++) {
			String password = SHA256.encrypt(phoneList.get(i));
			String result = "UPDATE system_user SET PASSWORD='"+password+"' WHERE SYSTEM_ID='"+salesmanIdList.get(i)+"';";
			System.out.println(result);
		}
		
		int count = Runtime.getRuntime().availableProcessors();
		System.out.println(count);
		
		System.out.println("YTVmZTlkYzBmOWY4ZjNjMGQ5MTAyOWY4NTcyYWE4ZTRjMmEyMGUyYjNjZWNiZGY4YjQ4MzA1MDEzMjFlYTY3Yw==".equals("NTlmYjU0NWQ1Y2M0ZDZmOGE2NDVmMTE2NGU3NmVkM2JiNDA3OGY4Nzc2OGIyMTY1OGE2YjY2NjFkMGNlNTE4Nw=="));

	}
	

	private static void setInfo(List<String> phoneList, List<String> salesmanIdList) {
		phoneList.add("123456");
		salesmanIdList.add("10101");
		phoneList.add("123456");
		salesmanIdList.add("10313");
		phoneList.add("123456");
		salesmanIdList.add("10314");
		phoneList.add("123456");
		salesmanIdList.add("10339");
		phoneList.add("123456");
		salesmanIdList.add("10360");
		phoneList.add("123456");
		salesmanIdList.add("10488");
		phoneList.add("123456");
		salesmanIdList.add("10494");
		phoneList.add("123456");
		salesmanIdList.add("10510");
		phoneList.add("123456");
		salesmanIdList.add("13505");

	}
}
