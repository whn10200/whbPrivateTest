package com.whb.java8;

public interface JavaVsersion8 {
	
	void read(String str);
	
	default void write(String str) {
		System.out.println("write:==="+str);
	}

}
