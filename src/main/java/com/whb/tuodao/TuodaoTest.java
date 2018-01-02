package com.whb.tuodao;

import org.junit.Test;

/**
 * @author whb
 * @date 2017年12月29日 上午9:48:40 
 * @Description: 互金请求拓道业务系统方式demo
 */
public class TuodaoTest {

	
	/* 需要引入jar包
	 * <dependency>
		<groupId>com.fasterxml.jackson.dataformat</groupId>
		<artifactId>jackson-dataformat-xml</artifactId>
	   </dependency>
	*/
	
	
	@Test
	public void test() throws Exception {
		
		Demo input = new Demo();
		//必传
		input.setUserName("78989");
		input.setIdCard("340826199209094899");
		input.setBankAccountId("ICBC");
		input.setBankCardId("6222021001098942458");
		input.setBankPhone("17682307299");
		
		String result = HttpUtil.doService("http://127.0.0.1:10001/", "opertLoanUser", "accessKey", HttpUtil.ContentType.JSON, input);
		
		System.out.println(result);
		
		//返回{"response":{"info":{"code":100000,"msg":"成功"},"content":"success"}}
	}

}
