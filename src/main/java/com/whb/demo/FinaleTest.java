package com.whb.demo;

import java.math.BigInteger;

public class FinaleTest {
	
	private final BigInteger lastNum;
	
	private final int age=11;
	
	public static void main(String[] args) {
		Integer tt = 4;
		Integer ss = 4;
		if(tt==ss)
			System.out.println(456);
	}

	public FinaleTest(BigInteger lastNum) {
		super();
		this.lastNum = lastNum;
	}

	public BigInteger getLastNum() {
		return lastNum;
	}

	public int getAge() {
		return age;
	}
	 
	

}
