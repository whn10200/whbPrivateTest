package com.whb.utils;

import com.whb.utils.time.DateUtils;

public class SerialNumberUtil {

	
	public static String PROJECT_NO = "PN";
	
	public static String REPAY_LINE_NO = "MX";
	
	public static String REPAY_PAY_NO = "PC";
	
	public static String generateProjectSerialNo(Integer repayId) {
		String dateline = DateUtils.formatDatetoString(
				DateUtils.getCurrentDate(), DateUtils.TIME_PATTERN_MILLISECOND);
		String repayIdStr = repayId.toString();
		if(repayIdStr.length()<=6) {
			return  dateline + addZeros(repayIdStr,6);
		} else {
			return  dateline + repayIdStr.substring(repayIdStr.length() - 6,
					repayIdStr.length());
		}
	}
	
	public static String addZeros(String str, int len) {
	    if (str.length() >= len) return str;
	     
	    len -= str.length();
	    StringBuilder sb = new StringBuilder();
	    while (len-- > 0) {
	        sb.append('0');
	    }
	    sb.append(str);
	     
	    return sb.toString();
	}
	
	
	public static String generateProjectSsn(String Ssn) {
		if(Ssn.length()<=6) {
			return  addZeros(Ssn,6);
		} else {
			return Ssn.substring(Ssn.length() - 6,
					Ssn.length());
		}
	}
	
	public static String generateRepayLinePaySn(String payNo) {
		String dateline = REPAY_LINE_NO+DateUtils.formatDatetoString(
				DateUtils.getCurrentDate(), DateUtils.DATE_FMT_0);
		String payNoStr = payNo.toString();
		if(payNoStr.length()<=6) {
			return  dateline + addZeros(payNoStr,6);
		} else {
			return  dateline + payNoStr.substring(payNoStr.length() - 6,
					payNoStr.length());
		}
	}
	
	 public static String getPaySn(String payNo,String orderno){
	        String[] strs = payNo.split(REPAY_PAY_NO);
	        String paySn = REPAY_LINE_NO+strs[1];
	        if (orderno.length()<=6){
	        	return  paySn + addZeros(orderno,6);
	        }else{
	        	return paySn = paySn+orderno;
	        }
	    }

	public static void main(String[] args) {
		System.out.println(generateProjectSerialNo(0));
	}

}
