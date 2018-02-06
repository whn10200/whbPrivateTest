package com.whb.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-10-16 上午11:23:38
 * @since
 */
public class DateUtils {
	
	
	
	public static final String TIME_PATTERN_MILLISECOND = "yyyyMMddHHmmssSSS";
	
	public static final String TIME_PATTERN_SESSION = "yyyyMMddHHmmss";
	
	public static final String DATE_FMT_0 = "yyyyMMdd";

	/**
	 * 获取当前Date
	 * 
	 * @return date
	 */
	public static Date getCurrentDate() {
		Date today = new Date();
		return today;
	}

	/**
	 * 获取当前Calendar
	 * 
	 * @return 当前Calendar
	 */
	public static Calendar getCurrentCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}

	/**
	 * 
	 * @param date
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-6 下午03:32:25 <br>
	 * 
	 * @param formatString
	 * @param strDate
	 */
	public static Date convertStringToDate(String formatString, String strDate) {
		if (StringUtils.isBlank(strDate)) {
			throw new IllegalArgumentException("arg strDate[" + strDate + "] format is wrong");
		}
		if (StringUtils.isBlank(formatString)) {
			throw new IllegalArgumentException("arg formatString[" + formatString + "] format is wrong");
		}

		SimpleDateFormat df = new SimpleDateFormat(formatString);
		Date date = null;

		try {
			date = df.parse(strDate);
		} catch (ParseException ex) {
			throw new IllegalArgumentException("arg formatString[" + formatString + "] format is wrong", ex);
		}

		return (date);
	}

	/**
	 * 
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-6 下午03:32:35 <br>
	 * 
	 * @param formatString
	 * @param date
	 */
	public static String formatDate(String formatString, Date date) {
		SimpleDateFormat df = null;
		String returnValue = null;

		if (date != null) {
			df = new SimpleDateFormat(formatString);
			returnValue = df.format(date);
		}

		return returnValue;
	}
	
	/**
	 * 根据当前时间得到一个月前的时间 只包括 年月日
	* @return
	* <p>
	* author: xuyuanyang<br>
	* create at: 2014年4月16日上午12:34:54
	 */
	public static String getDateBefore1M(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		int date = now.get(Calendar.DAY_OF_MONTH);
		int[] smallMonths = {2,4,6,9,11};
		if(month-1<=0){ //跨年
			year = year -1;
			month = 12;
		}
		if((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0) && month==3){ //如果1月前是闰年的2月，那么该月的最大天数是28
			month=2;
			date = date>28 ? 28 : date;
		}else if(Arrays.asList(smallMonths).contains(month-1)){//如果1月前是小月，那么最大的天数是30
			month=month-1;
			date = date>30 ? 30 : date;
		}else{
			month = month-1;
		}
		String months = String.valueOf(month).length()==1 ? "0"+month : ""+ month;
		String dates = String.valueOf(date).length()==1 ? "0"+date : ""+ date; 
		return year+"-"+months+"-"+dates;
	}


	/**
	 * 获取日期
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static final int getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 计算两个日期的月份
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getMonthsBetweenDates(Date startDate, Date endDate)
	{
		if(startDate.getTime() > endDate.getTime())
		{
			Date temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		int yearDiff = endCalendar.get(Calendar.YEAR)- startCalendar.get(Calendar.YEAR);
		int monthsBetween = endCalendar.get(Calendar.MONTH)-startCalendar.get(Calendar.MONTH) +12*yearDiff;

		if(endCalendar.get(Calendar.DAY_OF_MONTH) >= startCalendar.get(Calendar.DAY_OF_MONTH))
			monthsBetween = monthsBetween + 1;
		return monthsBetween;

	}

	public static int getIntervalDays(Date firstDate, Date lastDate) {
		if (null == firstDate || null == lastDate) {
			return -1;
		}

		long intervalMilli = lastDate.getTime() - firstDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}

	/**
	 * 两个时间间隔除以24小时
	 *
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static int daysOfTwo(Date firstDate, Date lastDate) {
		return getIntervalDays(firstDate, lastDate);
	}

	/**
	 * 增加月数
	 *
	 * @param date
	 * @param month
	 *            需要增加的月数，比如需要增加2个月，就传入2
	 * @return
	 */

	public static Date addMonth(Date date, int month) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (month != 0) {
			calendar.add(Calendar.MONTH, month);
		}
		return calendar.getTime();
	}

	/**
	 * 日期转字串
	 *
	 * @param date
	 * @return
	 */
	public static String getDateStrFromDate(Date date) {
		return getStrFromDate(date, "yyyy-MM-dd");
	}

	public static String getStrFromDate(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		if(date==null){
			return "";
		}
		String s = df.format(date);
		return s;
	}

	public static int calDiffMonth(String startDate,String endDate){
        int result=0;
        try {
            SimpleDateFormat sfd=new SimpleDateFormat("yyyy-MM-dd");
            Date start = sfd.parse(startDate);
            Date end = sfd.parse(endDate);
            int startYear=getYear(start);
            int startMonth=getMonth(start);
            int startDay=getDay(start);
            int endYear=getYear(end);
            int endMonth=getMonth(end);
            int endDay=getDay(end);
            if (startDay>endDay){ //1月17  大于 2月28
                if (endDay==getDaysOfMonth(getYear(new Date()),2)){   //也满足一月
                    result=(endYear-startYear)*12+endMonth-startMonth;
                }else{
                    result=(endYear-startYear)*12+endMonth-startMonth-1;
                }
            }else{
                result=(endYear-startYear)*12+endMonth-startMonth;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

 public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

 public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

 public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

 public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

	/**
	 * 增加天数
	 *
	 * @param date
	 * @param day
	 *            需要增加的天数，比如需要增加2天，就传入2
	 * @return
	 */

	public static Date addDay(Date date, int day) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (day != 0) {
			calendar.add(Calendar.DATE, day);
		}
		return calendar.getTime();
	}

	/**
	 * 增加天数
	 *
	 * @param date
	 * @param day
	 *            需要增加的天数，比如需要增加2天，就传入2
	 * @return
	 */

	public static Date subDay(Date date, int day) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (day != 0) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
		}
		return calendar.getTime();
	}

	/**
	 * 
	 * @Description:指定时间的上个月
	 * @param date
	 * @param day
	 * @return
	 * @author: chaisen
	 * @time:2017年4月11日 下午2:01:14
	 */
	public static Date lastMonth(Date date, int month) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (month != 0) {
			 calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
		}
		return calendar.getTime();
	}
	/**
	 * 
	 * @Description:时间格式化
	 * @param date
	 * @return
	 * @author: chaisen
	 * @time:2017年4月11日 下午2:02:27
	 */
	public static String getDateStrFromDateYM(Date date) {
		return getStrFromDate(date, "yyyy-MM");
	}
	/**
	 * 
	 * @Description:比较时间大小
	 * @param firstDate
	 * @param lastDate
	 * @return
	 * @author: chaisen
	 * @time:2017年4月11日 下午2:24:22
	 */
	public static int compareDate(Date firstDate, Date lastDate) {
		firstDate = getDateFromString(formatDatetoString(firstDate, "yyyy-MM-dd"));
		lastDate  = getDateFromString(formatDatetoString(lastDate, "yyyy-MM-dd"));
		long first = firstDate.getTime();
		long last = lastDate.getTime();
		if (first == last) {
			return 0;
		} else {
			return (first > last) ? 1 : 2;
		}
	}
	
	public static void main(String args[]){
		Date firstDate=getDateFromString("2016-12-31");
		Date lastDate=getDateFromString("2017-04-10");
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(2017, 3, 12);
		System.out.println(getMonthsBetweenDatesNoDay(lastDate, firstDate));
	}
	
	/**
	 * 字串转为日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDateFromString(String dateStr) {
		return getDateFromString(dateStr, null);
	}
	
	/**
	 * 字串转为日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date getDateFromString(String dateStr, String pattern) {
		if ((pattern == null) || ("".equals(pattern))) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			date = null;
		}
		return date;
	}
	
	public static String formatDatetoString(Date d, String pattern) {
		if (null == d || StringUtils.isBlank(pattern)) {
			return null;
		}

		SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();

		formatter.applyPattern(pattern);
		return formatter.format(d);
	}
	
	/**
	 * 计算两个日期的月份(不考虑天)
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getMonthsBetweenDatesNoDay(Date startDate, Date endDate)
	{
		if(startDate.getTime() > endDate.getTime())
		{
			Date temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		int yearDiff = endCalendar.get(Calendar.YEAR)- startCalendar.get(Calendar.YEAR);
		int monthsBetween = endCalendar.get(Calendar.MONTH)-startCalendar.get(Calendar.MONTH) +12*yearDiff;

		/*if(endCalendar.get(Calendar.DAY_OF_MONTH) >= startCalendar.get(Calendar.DAY_OF_MONTH))
			monthsBetween = monthsBetween + 1;*/
		return monthsBetween;

	}
	
	public static final Date zerolizedTime(Date fullDate) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(fullDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	public static final Date getSpecialTime(Date date,int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);
		return cal.getTime();
	}
	
	
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate)    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate)); 
		} catch (ParseException e) {
			e.printStackTrace();
		}  
         
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }   
    /**
	 * 以日历的角度计算两个日期（忽略时分秒）间隔的天数，同日算一天。
	 * 
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
    public static int daysByCalendar(Date firstDate, Date lastDate) {
		firstDate = formatDate(firstDate, "yyyy-MM-dd");
		lastDate  = formatDate(lastDate, "yyyy-MM-dd");  
		if(getDateStrFromDate(firstDate).equals(getDateStrFromDate(lastDate))){
			return 1;
			
		}
		int days=getIntervalDays(firstDate, lastDate);
		return days;
	}
    
    
    
    public static Date formatDate(Date d, String pattern) {
		return DateUtils.getDateFromString(formatDatetoString(d, pattern));
	}
    
    
	public static Date getYesterDay() {
		return addDate(getCurrentDate(), -1);
	}
	
	/**
	 * 增加天数
	 * 
	 * @param date
	 * @param day
	 * @return Date
	 */
	public static Date addDate(Date date, int day) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:获取指定日期的前一天
	 * @param date
	 * @return
	 * @time:2017年9月23日 下午4:07:12
	 */
	public static Date getSomeDayYesterDay(Date date) {
		return addDate(date, -1);
	}
	
	/**
	 * 生成交易流水号
	 * @param type
	 * @return
	 * @return String
	 */
	public static String createSerial(String type)  
    {  
        SimpleDateFormat sdft = new SimpleDateFormat("yyMMddhhmmss");  
        Date nowdate = new Date();  
        String str = sdft.format(nowdate);  
        return type + str + RandomStringUtils.randomNumeric(3);  
    }
}
