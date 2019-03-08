package com.whb.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.Lists;

public class LambdaTest {

	@Test
	public void test() {
		List<String> names = new ArrayList<>();
		names.add("TaoBao");
		names.add("ZhiFuBao");
		List<String> lowercaseNames = names.stream().map((String name) -> {
			return name.toLowerCase();
		}).collect(Collectors.toList());
		for (String string : lowercaseNames) {
			System.out.println(string);
		}
	}
	
	
	@Test
	public void test3() {
		List<Integer> list = Lists.newArrayList(1,2,5,3,9,4);
		List<Integer> collect = list.stream().sorted(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		}).collect(Collectors.toList());
		
		collect.forEach(s ->{
			System.out.println(s);
		});
	}

	@Test
	public void test2() {

		// Lists是Guava中的一个工具类
		List<Integer> nums1 = Lists.newArrayList(1, null, 3, 4, null, 6);
//		System.out.println(nums1.stream().filter(num -> num != null).count());
		
		List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
//		nums.stream().filter(num -> num != null).distinct();
//		for (Integer tt : nums) {
//			System.out.println(tt);
//		}
		
		System.out.println("sum is:" + nums.stream().filter(num -> num != null).distinct().mapToInt(num -> num * 2)
				.peek(System.out::println).skip(2).limit(4).sum());
	}

}
