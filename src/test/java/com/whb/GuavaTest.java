package com.whb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * @author whb
 * @date 2018年2月7日 上午10:25:05 
 * @Description: Guava工程包含了若干被Google的 Java项目广泛依赖 的核心库，
 * 例如：集合 [collections] 、缓存 [caching] 、原生类型支持 [primitives support] 、
 * 并发库 [concurrency libraries] 、通用注解 [common annotations] 、
 * 字符串处理 [string processing] 、I/O 等等。
 * guava类似Apache Commons工具集
 */
public class GuavaTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		long maxMemory = Runtime.getRuntime().maxMemory();// 返回Java虚拟机试图使用的最大内存量。
		Long totalMemory = Runtime.getRuntime().totalMemory();// 返回Java虚拟机中的内存总量。
		System.out.println("MAX_MEMORY =" + maxMemory + "(字节)、" + (maxMemory / (double) 1024 / 1024) + "MB");
		System.out.println("TOTAL_ MEMORY = " + totalMemory + "(字节)" + (totalMemory / (double) 1024 / 1024) + "MB");
	}
	

	@Test
	public void testJoiner() {
		/*
		 * on:制定拼接符号，如：test1-test2-test3 中的 “-“ 符号
		 * skipNulls()：忽略NULL,返回一个新的Joiner实例
		 * useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替
		 */
		StringBuilder sb = new StringBuilder();
		Joiner.on(",").skipNulls().appendTo(sb, "Hello", "guava");
		System.out.println(sb);
		System.out.println(Joiner.on(",").useForNull("none").join(1, null, 3));
		System.out.println(Joiner.on(",").skipNulls().join(Arrays.asList(1, 2, 3, 4, null, 6)));
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(map));
	}
	
	@Test
	public void testSplitter() {
		/*
		 * on():指定分隔符来分割字符串 
		 * limit():当分割的子字符串达到了limit个时则停止分割
		 * fixedLength():根据长度来拆分字符串 
		 * trimResults():去掉子串中的空格
		 * omitEmptyStrings():去掉空的子串
		 * withKeyValueSeparator():要分割的字符串中key和value间的分隔符
		 * 分割后的子串中key和value间的分隔符默认是=
		 */
		System.out.println(Splitter.on(",").limit(3).trimResults().split(" a,  b,  c,  d"));
		// [ a, b, c,d]
		System.out.println(Splitter.fixedLength(3).split("1 2 3"));
		// [1 2, 3]
		System.out.println(Splitter.on(" ").omitEmptyStrings().splitToList("1  2 3"));
		System.out.println(Splitter.on(",").omitEmptyStrings().split("1,,,,2,,,3"));
		// [1, 2, 3]
		System.out.println(Splitter.on(" ").trimResults().split("1 2 3"));
		// [1, 2, 3],默认的连接符是,
		System.out.println(Splitter.on(";").withKeyValueSeparator(":").split("a:1;b:2;c:3"));
		// {a=1, b=2, c=3}
	}

}
