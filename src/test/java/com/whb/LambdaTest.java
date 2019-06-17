package com.whb;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.whb.LambdaTest.Student;

import lombok.Data;

/**
 * @author whb
 * @date 2018年10月29日 下午4:28:07
 * @Description: JDK8的stram方法演示
 * 如果一个方法接受声明于 java.util.function 包内的接口，例如 Predicate、Function、Consumer 或 Supplier，那么可以向其传lambda表达式.
 * lambda表达式内可以使用方法引用，仅当该方法不修改lambda表达式提供的参数。本例中的lambda表达式可以换为方法引用，因为这仅是一个参数相同的简单方法调用。
 */
public class LambdaTest {

	/**
	 * lambda表达式内可以使用方法引用，仅当该方法不修改lambda表达式提供的参数。本例中的lambda表达式可以换为方法引用，因为这仅是一个参数相同的简单方法调用。
	 * list.forEach(n -> System.out.println(n));
	 * list.forEach(System.out::println);  // 使用方法引用
	 * 然而，若对参数有任何修改，则不能使用方法引用，而需键入完整地lambda表达式，如下所示：
	 * list.forEach((String s) -> System.out.println("*" + s + "*"));
	 * 事实上，可以省略这里的lambda参数的类型声明，编译器可以从列表的类属性推测出来。
	 */
	@Test
	public void testForEach() {
		List<Integer> list = Lists.newArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		// 直接打印
		list.forEach(System.out::println);
		list.forEach(Integer::floatValue);

		// 取值分别操作
		list.stream().forEach(i -> {
			System.out.println(i * 3);
		});
		
		list.parallelStream().forEach(s ->{
			System.out.println(s);
		});

		// 可改变对象,只在本次调用中有效, 并不会改变原有的list
		list.stream().map((i) -> i * 3).forEach(System.out::println);

		// 不可改变元有对象
		list.forEach(i -> i = i * 3);
		list.forEach(System.out::println);

		Integer integer = list.stream().map((i) -> i = i * 3).reduce((sum, count) -> sum += count).get();
		System.out.println(integer);
	}
	
	@Test
	public void test2() {
		new Thread(() -> System.out.println("In Java8!")).start();

		System.out.println(Joiner.on("_").join("11", "22", "33"));

		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

		System.out.println("Languages which starts with J :");
		filter(languages, (str) -> ((String) str).startsWith("J"));

		System.out.println("Languages which ends with a ");
		filter(languages, (str) -> ((String) str).endsWith("a"));

		System.out.println("Print all languages :");
		filter(languages, (str) -> true);

		System.out.println("Print no language : ");
		filter(languages, (str) -> false);

		System.out.println("Print language whose length greater than 4:");
		filter(languages, (str) -> ((String) str).length() > 4);

		Predicate<String> startWithJ = (n) -> n.startsWith("J");
		Predicate<String> fourLength = (n) -> n.length() == 4;

		languages.stream().filter(startWithJ.and(fourLength)).forEach(System.out::println);

	}

	/**
	 * redicate接口
	 * @param names
	 * @param condition
	 */
	private void filter(List<String> names, Predicate<String> condition) {
		for (String name : names) {
			if (condition.test(name)) {
				System.out.println(name + " ");
			}
		}
	}
	
	/**
	 * reduce, 用来将值进行合并, 又称折叠操作, Map和Reduce操作是函数式编程的核心操作
	 * SQL中类似 sum()、avg() 或者 count() 的聚集函数，实际上就是 reduce 操作，因为它们接收多个值并返回一个值。
	 * 流API定义的 reduceh() 函数可以接受lambda表达式，并对所有值进行合并。
	 * IntStream这样的类有类似 average()、count()、sum() 的内建方法来做 reduce 操作，
	 * 也有mapToLong()、mapToDouble() 方法来做转换
	 */
	@Test
	public void test3() {
		// 字符串连接，concat = "ABCD"
		String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
		System.out.println(concat);
		// 求最小值，minValue = -3.0
		double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
		System.out.println(minValue);
		// 求和，sumValue = 10, 有起始值
		int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
		System.out.println(sumValue);
		// 求和，sumValue = 10, 无起始值
		sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
		// 过滤，字符串连接，concat = "ace"
		concat = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") > 0).reduce("", String::concat);
		System.out.println("a".concat("b"));
	}

	
	

	/**
	 * 过滤是Java开发者在大规模集合上的一个常用操作，而现在使用lambda表达式和流API过滤大规模数据集合是惊人的简单。
	 * 流提供了一个 filter() 方法，接受一个 Predicate 对象，即可以传入一个lambda表达式作为过滤逻辑。
	 * 下面的例子是用lambda表达式过滤Java集合，将帮助理解。 
	 */
	@Test
	public void test4() {
		List<String> strList = Arrays.asList("abc", "eqwr", "bcd", "qb", "ehdc", "jk");
		List<String> filtered = strList.stream().filter(x -> x.length() > 2).collect(Collectors.toList());
		System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);

		// 对列表的每个元素使用 函数
		strList = Lists.newArrayList("abc", "eqwr", "bcd", "qb", "ehdc", "jk");
		String collect = strList.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
		System.out.printf("filtered list : %s %n", collect);

		// 使用distinct进行去重
		List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
		List<Integer> distinct = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
		System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
	}
	
	/**
	 * 计算最值和平均值
	 * IntStream、LongStream 和 DoubleStream 等流的类中，有个非常有用的方法叫做 summaryStatistics() 。
	 * 可以返回 IntSummaryStatistics、LongSummaryStatistics 或者 DoubleSummaryStatistic s，描述流中元素的各种摘要数据。
	 * 在本例中，我们用这个方法来计算列表的最大值和最小值。它也有 getSum() 和 getAverage() 方法来获得列表的所有元素的总和及平均值
	 */
	@Test
	public void test5() {
		// 获取数字的个数、最小值、最大值、总和以及平均值
		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
		System.out.println("Highest prime number in List : " + stats.getMax());
		System.out.println("Lowest prime number in List : " + stats.getMin());
		System.out.println("Sum of all prime numbers : " + stats.getSum());
		System.out.println("Average of all prime numbers : " + stats.getAverage());
	}
	
	
	/**
	 * 使用方法引用, 不对参数做任何修改方法引用有3种形式
     * 把lambda表达式的参数直接当成instanceMethod|staticMethod的参数来调用。
     * 比如System.out::println等同于x->System.out.println(x)；Math::max等同于(x, y)->Math.max(x,y)。
	 * objectName::instanceMethod
	 * ClassName::staticMethod
	 * 把lambda表达式的第一个参数当成instanceMethod的目标对象，其他剩余参数当成该方法的参数。
	 * 比如String::toLowerCase等同于x->x.toLowerCase()。
	 * ClassName::instanceMethod
	 */
	@Test
	public void test6() {

		// 使用String默认的排序规则，比较的是Person的name字段
		Comparator<Student> byName = Comparator.comparing(p -> p.getName());
		// 不用写传入参数,传入的用Person来声明
		Comparator<Student> byName2 = Comparator.comparing(Student::getName);

		List<Student> studentList = Arrays.asList(new Student("网三", 11), new Student("彰武", 55), new Student("赵四", 33));
		
		List<Student> collect = studentList.stream().sorted(Comparator.comparing(Student :: getAge)).collect(Collectors.toList());
		for (Student student : collect) {
			System.out.println(student.getName());
		}
		

		// 获取线程安全的Map
		ConcurrentHashMap<String, Student> currrentMap = (ConcurrentHashMap<String, Student>) studentList.stream()
				.collect(Collectors.toConcurrentMap(Student::getName, Student -> Student));

		// 老的方式
		Map<String, Integer> map = studentList.stream().collect(Collectors.toMap(new Function<Student, String>() {
			@Override
			public String apply(Student t) {
				return t.getName();
			}
		}, Student::getAge));

	}
	
	/**
	 * 实体类
	 */
	@Data
	class Student {
		private String name;
		private Integer age;

		public Student(String name, Integer age) {
			super();
			this.name = name;
			this.age = age;
		}
	}
}
