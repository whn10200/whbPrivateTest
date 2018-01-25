package com.whb.aop.jdk;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author whb
 * @date 2017年11月14日 上午10:24:02
 * @Description: TODO
 */
public class AopMain {

	public static void main(String[] args) {
		BusinessInterface realBusiness = new RealBusinessImpl();
		BusinessInvocationHandler invocationHandler = new BusinessInvocationHandler(realBusiness);

		/*
		 * 生成一个动态代理实例。里面的三个参数需要讲解一下： 1-loader：这个newProxyInstance会有一个返回值，即代理对象。
		 * 那么问题就是类实例的创建必须要有classloader的支持，第一个参数就是指等“代理对象”的创建所依据的classloader
		 * 
		 * 2-interfaces：第二个参数是一个数组。在设计原理中，有一个重要的原则是“依赖倒置”，它的实践经验是：“依赖接口，而不是以来实现”
		 * 。 所以，JAVA中动态代理的支持假定程序员是遵循这一原则的：所有业务都定义的接口。这个参数就是为动态代理指定“代理对象所实现的接口”，
		 * 由于JAVA中一个类可以实现多个接口，所以这个参数是一个数组（我的实例代码中，
		 * 只为真实的业务实现定义了一个接口BusinessInterface，
		 * 所以参数中指定的也就只有这个接口）.另外，这个参数的类型是Class，所以如果您不定义接口，而是指定某个具体类，也是可行的。
		 * 但是这不符合设计原则。
		 * 
		 * 3-InvocationHandler：这个就是我们的“调用处理器”，这个参数没有太多解释的
		 */
		BusinessInterface proxyBusiness = (BusinessInterface) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), new Class[] { BusinessInterface.class },
				invocationHandler);
		// 正式调用
//		proxyBusiness.dosomething("whb");

		// 以下方法也可以
		/*
		 * BusinessInterface proxyBusiness2 =
		 * (BusinessInterface)Proxy.newProxyInstance(
		 * realBusiness.getClass().getClassLoader(),
		 * realBusiness.getClass().getInterfaces(), invocationHandler);
		 * proxyBusiness2.dosomething("南北雪树");
		 */


		// 2、通过JDK提供的java.util.ServiceLoader工具类实例化这个接口了
		ServiceLoader<BusinessInterface> interfaces = ServiceLoader.load(BusinessInterface.class);
		// 这样写的原因是，您可以一次指定这个接口的多个具体实现
		Iterator<BusinessInterface> iinterface = interfaces.iterator();
		if (iinterface.hasNext()) {
			BusinessInterface interfaceItem = iinterface.next();
			interfaceItem.dosomething("南北雪树");
		}

	}

}
