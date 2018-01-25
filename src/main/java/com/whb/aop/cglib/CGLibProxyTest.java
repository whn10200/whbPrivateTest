package com.whb.aop.cglib;

import org.junit.Test;

public class CGLibProxyTest {

	@Test
	public void test() {
		ProductDao productDao = new ProductDao();
		ProductDao proxy = new CGLibProxy(productDao).createProxy();
		proxy.add();
		proxy.update();
	}
}
