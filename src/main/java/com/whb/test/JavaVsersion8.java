package com.whb.test;

public interface JavaVsersion8 {
	
	void read(String str);
	
	default void write(String str) {
		System.out.println("write:==="+str);
	}

}
