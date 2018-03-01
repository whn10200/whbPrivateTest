package com.whb.jdk;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import com.whb.model.Student;

import net.bytebuddy.description.modifier.SynchronizationState;

public class StringTest {

	@Test
	public void StringTest() {
		BigDecimal gps_month = new BigDecimal("0.16");
		System.out.println(gps_month);
		
		String[] s1 = new String[]{"a","b","c"};
		Student tt = new Student();
		/*String[] s2 = new String[s1.length+1];
		
		s2[0]=s1[0];
		s2[1]=s1[1];
		s2[2]=s1[2];
		
		//System.arraycopy(s1, 0, s2, 1, s1.length);
		
		s1[2]="w";
		//对s1[2]重新指向了一个新的字符串对象，并不是直接赋值。s2[2]还是指向原来的字符串对象。
		
		//s2[2]="u";
		
		
		System.out.println(Arrays.toString(s1));
		
		System.out.println(Arrays.toString(s2));*/
		
	}

}
