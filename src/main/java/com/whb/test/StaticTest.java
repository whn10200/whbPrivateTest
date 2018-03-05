package com.whb.test;

public class StaticTest {
	public static void main(String args[]) {
		//staticFunction();
		System.out.println("adc");
	}

	static StaticTest st = new StaticTest();
	
	
	static {
		System.out.println("3");
	}

	StaticTest() {
		System.out.println("2");
	}

	public static void staticFunction() {
		System.out.println("4");
	}

	{
		System.out.println("1");
	}
	int a = 100;
	static int b = 112;
}
