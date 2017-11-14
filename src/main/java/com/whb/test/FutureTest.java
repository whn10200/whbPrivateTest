package com.whb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.whb.demo.Student;
public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		List<Future<Student>> results = new ArrayList<Future<Student>>();
		ExecutorService es = Executors.newCachedThreadPool();
		List<Student> list = new ArrayList<Student>();
		for (int i = 0; i < 10; i++) {
			Student stu = new Student();
			stu.setAge(i);
			stu.setName("张三"+i);
			list.add(stu);
		}
		for (Student student : list) {
			results.add(es.submit(new TaskStuCallable(student)));
		}
		
		for (Future<Student> res : results){
			System.out.println(res.get().toString());
			list.add(res.get());
		}
	}
	
	public static class TaskStuCallable implements Callable<Student> {
		private Student stu;
		public TaskStuCallable(Student stu) {
			super();
			this.stu = stu;
		}
		@Override
		public Student call() throws Exception {
			String tid = String.valueOf(Thread.currentThread().getId());
			System.out.printf("Thread#%s : in call\n", tid);
			stu.setAge(Integer.valueOf(tid));
			stu.setName(stu.getName()+"----"+tid);
			return stu;
		}
	}
}
