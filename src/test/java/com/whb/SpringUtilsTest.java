package com.whb;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.redisson.connection.CRC16;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
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
		
		Integer tt =2;
		int ss =2;
		System.out.println(tt==ss);
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
		
//		CRC16(‘name’)%16384 
		System.out.println(CRC16.crc16("王".getBytes()));
		System.out.println(CRC16.crc16("王".getBytes())%1638);
	}
	
	@Test
	public void test4(){
		/*for (int i = 0; i < 5; i++) {
			if(i==2){
				System.out.println(222);
				continue;
			}
			System.out.println(i);
		}*/
		
/*		System.out.println(GetMD5Code("数组大小一般取质数"));
		System.out.println(hashKeyForDisk("数组大小一般取质数"));
		System.out.println(toHash("数组大小一般取质数"));
		System.out.println("数组大小一般取质数".hashCode());*/
		
		
		String ss = "{'code': '0000', 'result': {'ss':'CERT201708300057_20180109_1515464714812','ff':'CERT201708300057_20190309_1515464714816'}}";
		String tt = JSON.parseObject(ss).getString("code");
		System.out.println(JSON.parseObject(ss).getJSONObject("result").getString("ss"));
			                                                                                                              
	}
	
	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				resultString = byteToString(md.digest(strObj.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}
	
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}
	
	private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d","e", "f" };
	
	public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
	
	private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
	
	// 将字符串转成hash值
	public static int toHash(String key) {
		int arraySize = 11113; // 数组大小一般取质数
		int hashCode = 0;
		for (int i = 0; i < key.length(); i++) { // 从字符串的左边开始计算
			int letterValue = key.charAt(i) - 96;// 将获取到的字符串转换成数字，比如a的码值是97，则97-96=1
													// 就代表a的值，同理b=2；
			hashCode = ((hashCode << 5) + letterValue) % arraySize;// 防止编码溢出，对每步结果都进行取模运算
		}
		return hashCode;
	}
	

	@Test
	public void test5(){
		String str1="a";
		String str2="b";
		String str3="ab";
		String str4 = str1+str2;
		String str5=new String("ab");
		System.out.println(str5==str3);//堆内存比较字符串池
		 //intern如果常量池有当前String的值,就返回这个值,没有就加进去,返回这个值的引用
		System.out.println(str5.intern()==str3);//引用的是同一个字符串池里的
		System.out.println(str5.intern()==str4);//变量相加给一个新值，所以str4引用的是个新的
		System.out.println(str4==str3);//变量相加给一个新值，所以str4引用的是个新的
		System.out.println(str4==str5);
		/*重点: --两个字符串常量或者字面量相加，不会new新的字符串,其他相加则是新值,(如 String str5=str1+"b";)
		因为在jvm翻译为二进制代码时，会自动优化，把两个值后边的结果先合并，再保存为一个常量。*/
		System.out.println("===================");
		//创建方式						对象个数		引用指向
		String a="abc";					//1			常量池
		String b=new String("abc");;	//1			堆内存 (abc则是复制的常量池里的abc)
		String c=new String();			//1			堆内存
		String d="a"+"bc";				//3			常量池(a一次，bc一次，和一次，d指向和)
		String e=a+b;					//3			堆内存(新的对象)
		String f="a";
		String g="bc";
		String h=f+g;
		String k="abc";	
		System.out.println(b==h);
		System.out.println(a==d);
		System.out.println(a==h.intern());
		System.out.println(a==k);
		System.out.println(a==f+"bc");
		System.out.println(b==f+"bc");
		
	}
	
	@Test
	public void test6(){
		List<Student> list = Lists.newArrayList();
		Student st = null;
		for (int i = 0; i < 2; i++) {
			st = new Student(i);
			System.out.println(st.hashCode());
			list.add(st);
			st = null;
		}
		
		for (Student st8 : list) {
			System.out.println(st8.hashCode());
		}
		
	}

}





