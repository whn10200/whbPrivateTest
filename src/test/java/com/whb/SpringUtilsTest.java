package com.whb;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.whb.model.Student;

public class SpringUtilsTest {
	
	private static Student source;
	
	private Boolean flag;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		source = new Student(11, "王五");
	}

	@Test
	public void testBeanUtils() {
		for (int i = 0; i < 5; i++) {
			if(i==1){
				System.out.println(" ee er 45e 565 ".replace(" ", ""));
				break;
			}
			System.out.println(456);
		}
		
		/*ArrayList<String> list = new ArrayList<String>();  
        list.add("one");  
        list.add("two");  
        list.add("two");  
        list.add("two");  
        list.add("two");  
        for(int i=0;i<list.size();i++){  
            if(list.get(i).equals("two")){  
                list.remove(i);  
            }  
        }  
        System.out.println(list); */ 
		
		/*System.out.println(getFlag());
		
		Student target = new Student();
		BeanUtils.copyProperties(source, target);
		System.out.println("source---->"+source.toString());
		System.out.println("target---->"+target.toString());*/
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
