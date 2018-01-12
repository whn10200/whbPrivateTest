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
	
	/**
	 * 根据手机号或者身份证号查询数据
	 * @throws Exception
	 * @return void
	 */
	@Test
	public void testFindLoanUser() throws Exception {
		Demo input = new Demo();
		input.setIdCard("340826199209094899");
		input.setPhone("17682307299"); 
		
		String result = HttpUtil.doService("http://127.0.0.1:10001/", "queryLoanUser", "accessKey", HttpUtil.ContentType.JSON, input);
		
		System.out.println(result);
	}
	
	
	/**
	 * 测试同步(新增或者修改)数据
	 * @throws Exception
	 * @return void
	 */
	@Test
	public void testAddOrUpdate() throws Exception {
		
		Demo input = new Demo();
		//必传
		input.setUserName("钟馗王");
		input.setIdCard("340826199209094123");
		input.setBankAccountId("ICBC");
		input.setBankCardId("6222021001098942123");
		input.setBankPhone("17682307123");
		
		String result = HttpUtil.doService("http://127.0.0.1:10001/", "operationLoanUser", "accessKey", HttpUtil.ContentType.JSON, input);
		
		System.out.println(result);
		
		//返回{"response":{"info":{"code":100000,"msg":"成功"},"content":"success"}}
	}

}
