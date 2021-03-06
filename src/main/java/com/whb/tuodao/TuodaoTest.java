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
//		input.setIdCard("340826199209094899");
		input.setPhone("13722979750"); 
//		http://tangjianying.f3322.net:10001/
//		http://120.27.163.111:10001//finace/getRepayList.json
		String result = HttpUtil.doService("http://120.27.163.111:10001/", "finace/queryLoanUser", HttpUtil.ContentType.JSON, input);
//		String result = HttpUtil.doService("http://127.0.0.1:10001/", "finace/queryLoanUser", HttpUtil.ContentType.JSON, input);
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
		input.setUserName("张小凡2");
		input.setIdCard("340823199412316819");
		input.setBankAccountId("CCB");
		input.setBankCardId("6236683820000245703");
		input.setBankPhone("17682307202");
		
		String result = HttpUtil.doService("http://127.0.0.1:10001/", "finace/operationLoanUser", HttpUtil.ContentType.JSON, input);
		//http://114.55.30.32:10003/
		System.out.println(result);
		
		//返回{"response":{"info":{"code":100000,"msg":"成功"},"content":"success"}}
	}
	
	@Test
	public void testGetRepayList() throws Exception {
		
		Demo input = new Demo();
		//必传
		input.setIdCard("421381199309128112");
		input.setRepayStatus(1);
		String result = HttpUtil.doService("http://120.55.193.48:10003/", "finace/getRepayList", HttpUtil.ContentType.JSON, input);
		//http://114.55.30.32:10003/
		System.out.println(result);
		
		//返回{"response":{"info":{"code":100000,"msg":"成功"},"content":"success"}}
	}
	
	@Test
	public void testUpdatePrivatePhone() throws Exception {
		
		Demo input = new Demo();
		//必传
		input.setUserName("吴国华");
		input.setIdCard("412827197503191034");
		input.setBankPhone("17682307202");
		
		String result = HttpUtil.doService("http://127.0.0.1:10001/", "finace/operationPritatePhone", HttpUtil.ContentType.JSON, input);
		//http://114.55.30.32:10003/
		System.out.println(result);
		
		//返回{"response":{"info":{"code":100000,"msg":"成功"},"content":"success"}}
	}

}
