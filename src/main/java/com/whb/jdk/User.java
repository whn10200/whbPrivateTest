package com.whb.jdk;

/**
 * @author whb
 * @date 2018年3月6日 下午5:00:12
 * @Description: 代码块与静态代码块与静态方法的加载顺序
 */
public class User {
	
	{
		System.out.println("进入代码块");
	}

	public static User user = new User("wang", 18);

	public static void userSay() {
		System.out.println("调用userSay静态方法");
		System.out.println("\n");
		//user.say();
	}

	static {
		//User.userSay();
		System.out.println("===========进入静态构造代码块==============");
		System.out.println("\n");
		//User user2333 = new User("wang2333", 19);
		//user2333.say();
		//user.say();
	}

	

	private String name;
	private int age;

	public User() {
		System.out.println("进入无参数构造函数");
	}

	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void say() {
		System.out.println(this.getName() + "调用say方法");
	}

}
