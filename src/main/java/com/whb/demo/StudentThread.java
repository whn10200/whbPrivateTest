package com.whb.demo;

public class StudentThread implements Runnable {
	
	private int num;

	public StudentThread(int num) {
		super();
		this.num = num;
	}

	@Override
	public void run() {
		Student stu = new Student(num);
		stu.valideAge();
		//System.out.println(Thread.currentThread().getName() + stu.getAge());
	}

}
