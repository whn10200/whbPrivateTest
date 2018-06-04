package com.whb;

import java.util.Random;

import org.junit.Test;

import com.whb.utils.HttpClientUtils;

public class BankCardInfoTest {

	@Test
	public void getBankCardInfo() throws Exception {
		String bankCard = "6217231205000703074";
		String str = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo={0}&cardBinCheck=true";
		String url = String.format(str, bankCard);
		byte[] bytes = HttpClientUtils.getBytes(url);
		if (bytes == null || bytes.length <= 0) {
		}
		System.out.println(new String(bytes, "utf-8"));;
	}
	
	@Test
	public void randrom(){
		Random random = new Random();
		System.out.println(random.nextInt(1000));
		System.out.println(random.nextLong());
	}

}
