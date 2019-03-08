package com.whb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.whb.model.Student;

import scala.collection.mutable.StringBuilder;

public class SpringUtilsTest {
	
	private static Student source;
	
	private Boolean flag;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		source = new Student(11, "王五");
		
//		AtomicReference<V>
//		Executors
	}
	
	@Test
	public void stringtest() {
		/*String ss= "440000_441800";
		System.out.println(ss.indexOf("_"));
		System.out.println(ss.substring(ss.indexOf("_")+1));
		int tt = 2;
		tt -= 4;
		System.out.println(tt);*/
		
		HashMap<String, List<String>> noLineGpsMap = Maps.newHashMap();
		for(Entry<String, List<String>> entry : noLineGpsMap.entrySet()){
			System.out.println(11);
		}
		
		System.out.println(Double.valueOf("20.3665"));
//		System.out.println(Long.valueOf("20.36"));
	}
	
	@Test
	public void listTest() {
		
		System.out.println("123".equals(null));
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
	
	@Test
	public void hashCodeAndEquilsTest() {
		Student tt = new Student(2,"12");
		System.out.println(tt.hashCode());
		
		System.out.println("12".hashCode());
		
		Student ss = new Student(2,"23");
		System.out.println(ss.hashCode());
		
		System.out.println(tt.equals(ss));
		
		System.out.println("123".equals("456"));
	}
	
	@Test
	public void studentTest() {
		Student tt = new Student(2,"12");
		Student ss = new Student(3);
		//BeanUtils.copyProperties(ss, tt);
		
		//System.out.println("tt"+tt.toString());
		
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(tt, ss);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("tt:"+tt.toString());
	}
	
	
	@Test
	public void test2() {
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			list.add(i);
//			System.out.println("1111111"+i);
//			for (int t = 0; t < 3; t++) {
//				System.out.println("222222"+t);
//				if(t==1){
//					break;
//				}
//			}
		}
		list.remove(0);
		System.out.println(list.toString());
		System.out.println(list.get(0));
	}
	
	@Test
	public void test3(){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		System.out.println(formatter.format(new Date()));
		
		System.out.println(new Date().getTime());
	}
}





