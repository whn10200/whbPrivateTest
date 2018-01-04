package com.whb.imageProject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.whb.imageProject.exception.BusinessCode;
import com.whb.imageProject.exception.BusinessException;
/**
 * 高并发日期处理工具类
 * 
 * @author jiamin
 */
public class DateUtils {
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public final static String FORMAT_DATE_ZH = "yyyy年MM月dd日";
	public final static String FORMAT_DATETIME_ZH = "yyyy年MM月dd日 HH时mm分ss秒";

	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
	private static final Object object = new Object();

	/**
	 * 获取SimpleDateFormat
	 * @param pattern 日期格式
	 * @return SimpleDateFormat对象
	 */
	private static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 将日期字符串转化为日期,日期字符串为空返回null
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return 格式化后的日期
	 * @throws BizException 日期格式化错误异常
	 */
	public static Date parseDate(String dateStr, String pattern) throws BusinessException {
		Date formatDate = null;
		if (dateStr != null && !dateStr.trim().equals("")) {
			try {
				formatDate = getDateFormat(pattern).parse(dateStr);
			} catch (Exception e) {
				throw new BusinessException("字符串转化成日期异常", BusinessCode._402);
			}
		}
		return formatDate;
	}

	/**
	 * 将日期转化为日期字符串，日期为null返回字符串null
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return 日期字符串
	 * @throws BizException 
	 */
	public static String formatDate(Date date, String pattern) throws BusinessException {
		String formatDateStr = null;
		if (date != null) {
			try {
				formatDateStr = getDateFormat(pattern).format(date);
			} catch (Exception e) {
				throw new BusinessException("日期转化成字符串异常", BusinessCode._402);
			}
		}
		return formatDateStr;
	}
	
	/**
	 * 将时间戳转化成,指定格式的日期 date
	 * @param date 日期是时间戳  长整型
	 * @param pattern 日期格式
	 * @return 日期字符串
	 * @throws BizException 
	 */
	public static String formatDate(long date, String pattern) throws BusinessException {
		try {
			return getDateFormat(pattern).format(date);
		} catch (Exception e) {
			throw new BusinessException("日期转化成字符串异常", BusinessCode._402);
		}
	}
}
