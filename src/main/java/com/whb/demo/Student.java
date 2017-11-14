package com.whb.demo;

import java.io.Serializable;

public class Student implements Serializable{
	
	/**@Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	private int age;
	
	private String name;

	
	
	public Student() {
		super();
	}

	public Student(int age) {
		super();
		this.age = age;
	}
	
	public Student(int age, String name) {
		this.age=age;
		this.name=name;
	}

	public void valideAge(){
		System.out.println(45);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [age=" + age + ", name=" + name + "]";
	}
	
	

}
