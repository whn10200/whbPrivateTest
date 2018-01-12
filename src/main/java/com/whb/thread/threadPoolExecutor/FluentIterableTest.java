package com.whb.thread.threadPoolExecutor;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.whb.model.Student;

public class FluentIterableTest {
	
	private static List<Student> studentList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Student u1 = new Student(20, "lili");
        Student u2 = new Student(19, "haha");
        Student u3 = new Student(20, "niu");
        Student u4 = new Student(23, "keke");

        studentList = Lists.newArrayList(u1, u2, u3, u4);
        
        
	}

	@Test
	public void fluentFliter() {
		/**
         * 获取 年龄==20的用户；返回为Iterable接口
         */
        FluentIterable<Student> filter = FluentIterable.from(studentList).filter(new Predicate<Student>() {
            @Override
            public boolean apply(Student Student) {
                return Student.getAge() == 20;
            }
        });
        //打印结果
        for (Student Student : filter) {
            System.out.println(Student.getAge());
        }
	}
	
	
    @Test
    public void fluentTransform() {
        /**
         * 转换集合类型   ,把list中的user对象转换为String
         */
        List<Student> transform = (List<Student>)FluentIterable.from(studentList).transform(new Function<Student, Student>() {
            @Override
            public Student apply(Student input) {
                //return Joiner.on("==").join(input.getAge(), input.getName());
            	Student u1 = new Student();
            	BeanUtils.copyProperties(input, u1);
            	return u1;
            }
        }).toList(); 
        
        //打印结果
        for (Student s : transform) {
            System.out.println(s.toString());
        }
    }
	

}
