package com.whb.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/** BigDecimal Calculator</br> 
 * new对象默认四舍五入，保留10位小数。
 * set后，舍入模式，保留小数都可以修改。舍入模式请调用BigDecimal的常量
 * */
public class ArithUtils {


	private static  final  BigDecimal ONE_HUNDRED = new BigDecimal(100);

	private static  final  BigDecimal TEN_THOUSAND = new BigDecimal(10000);
	/**
	 *  资金分转元 保留 位小数
	 *  decimal : 小数位数
	 */
	public static BigDecimal  toYuan(BigDecimal in,   Integer decimal)
	{
		if(in == null)
			in = new BigDecimal(0);
		 return  in.divide(ONE_HUNDRED,decimal, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 *  资金分转元 保留 2位小数
	 */
	public static BigDecimal  toYuan(BigDecimal in)
	{
		if(in == null)
			in = new BigDecimal(0);
		return  in.divide(ONE_HUNDRED,2, BigDecimal.ROUND_HALF_UP);
	}


	/**
	 *  资金分转万 保留 2位小数
	 */
	public static BigDecimal  toTenThousand(BigDecimal in)
	{
		if(in == null)
			in = new BigDecimal(0);
		return  in.divide(TEN_THOUSAND,2, BigDecimal.ROUND_HALF_UP);
	}


	/**
	 * 格式化金额显示 例如721,121.00
	 *
	 * @param money 金额（分）
	 * @return
	 */
	public static String formatCentToText(BigDecimal money) {
		if (Objects.isNull(money)) {
			return "0.00元";
		}

		/**  10w  */
		if(money.doubleValue()<10000000){
			DecimalFormat format = new DecimalFormat("#,##0.00元");
			return  format.format(toYuan(money));
		}else{
			DecimalFormat format = new DecimalFormat("#,##0.00万元");
			return  format.format(toTenThousand(money));
		}
	}



	/**
	 * 格式化金额显示 例如721,121.00
	 *
	 * @param money 金额（元）
	 * @return
	 */
	public static String formatYuanToText(BigDecimal money) {
		if (Objects.isNull(money)) {
			return "0.00元";
		}

		/**  10w  */
		if(money.doubleValue()<100000){
			DecimalFormat format = new DecimalFormat("#,##0.00元");
			return  format.format(money);
		}else{
			DecimalFormat format = new DecimalFormat("#,##0.00万元");
			return  format.format(toYuan(money));
		}

	}



	/**
	 *  资金分转元 没有小数
	 */
	public static  BigDecimal  toCent(BigDecimal in)
	{
		return  in.multiply(ONE_HUNDRED).setScale(2,   BigDecimal.ROUND_HALF_UP);
	}

}
