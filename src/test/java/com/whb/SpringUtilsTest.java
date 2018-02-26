package com.whb;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.whb.model.Student;

public class SpringUtilsTest {
	
	private static Student source;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		source = new Student(11, "王五");
	}

	@Test
	public void testBeanUtils() {
		Student target = new Student();
		BeanUtils.copyProperties(source, target);
		System.out.println("source---->"+source.toString());
		System.out.println("target---->"+target.toString());
	}

}
