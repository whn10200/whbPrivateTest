package com.whb;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.whb.model.Student;

import scala.collection.mutable.StringBuilder;

public class SpringUtilsTest {
	
	private static Student source;
	
	private Boolean flag;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		source = new Student(11, "王五");
	}
	
	@Test
	public void listTest() {
		ArrayList<String> list = new ArrayList<String>();
		/*for (int i = 0; i < 1000000; i++) {
			list.add(i+"");
		}*/
        list.add("one");  
        list.add("two");  
        list.add("two");  
        list.add("two");  
        list.add("two");
        StringBuilder build = new StringBuilder();
        long start = System.currentTimeMillis();
        System.out.println("foreach性能测试：");
        for (String temp : list) {
			build.append(temp);
		}
        System.out.println(System.currentTimeMillis()-start);
        
        System.out.println("\n Iterator性能测试：");
        start = System.currentTimeMillis();
        Iterator<String> iter2 = list.iterator();
        while(iter2.hasNext()){
        	String temp = iter2.next();
        	build.append(temp);
        }
        System.out.println(System.currentTimeMillis()-start);
        /*for(int i=0;i<list.size();i++){  
            if(list.get(i).equals("two")){  
                list.remove(i);  
            }  
        }*/ 
        
        Iterator<String> iter = list.iterator();
        while(iter.hasNext()){
        	String temp = iter.next();
        	if(temp.equals("two")){
        		iter.remove();
        	}
        		
        }
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
