package com.whb.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 字符串工具类
 * @author: 王艳兵
 * @date: 2017/9/4
 * @time: 17:47
 * @copyright: 拓道金服 Copyright (c) 2017
 */
public class StringUtil {

    private static final String MATCH_STR = "[A-Z]([a-z\\d]+)?";

    /**
     * 手机号码默认长度
     */
    private static final int MOBILE_LENGTH = 11;
    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camelUnderline(String line){
        if(line == null || "".equals(line)){
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb= new StringBuffer();
        Pattern pattern = Pattern.compile(MATCH_STR);
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "":"_");
        }
        return sb.toString();
    }

    /**
     * 隐藏银行卡位数
     * @param str 银行卡号
     * @param showLength 显示几个字符
     * @return 3012
     */
    public static String hideBankCard(String str,int showLength){
        if(str == null || str.length() < showLength){
            return str;
        }
        return  str.substring(str.length()- showLength);
    }

    /**
     * 借款人隐藏身份证
     * @param str
     * @return 411111******
     */
    public static String hideIdNum(String str){
        if(str == null || str.length() < 6){
            return str;
        }
        return str.substring(0,6) + "******";
    }


    public static String hideBankCard(String str){
        if(StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("argument is null or empty");
        }

        return "**** **** " + str.substring(8, str.length());
    }

    /**
     * 隐藏手机号码
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile){
        if(StringUtils.isBlank(mobile) || mobile.length() != MOBILE_LENGTH) {
            throw new IllegalArgumentException("argument is null or empty");
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * author : hechuan
     * @param value
     * @param type
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getValue(T value,Class<T> type,T defaultValue){
        return null !=value && value.getClass() == type ? value : defaultValue;
    }

    /**
     * 批量判断数据是否有空
     * @param strs
     * @return
     */
    public static boolean isNotEmptyBatch(String... strs){
        for (String str : strs) {
            if(str == null || "".equals(str))
                return false;
        }
        return true;
    }
    
	/** 获取userId中的手机号，不是mobile字段！！！如果没有连接符-则返回全部 */
	public static String getUserIdMobile(String userId) {
		String unionNo1 = userId.contains("-") ? userId.substring(0, userId.indexOf("-")) : userId;
		String unionNo2 = unionNo1.length() > 20 ? unionNo1.substring(0, 20) : unionNo1;
		return unionNo2;
	}
	
	
	public static String getNo(String head) {
		return head + "_" + new Long(System.currentTimeMillis()).toString();
	}

    /**
     * map转字符串 目前只用于打印银行返回结果信息
     * @param map
     * @return
     */
    public static String mapToString(Map<String,String> map){
        if(map == null){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String key :map.keySet()){
            sb.append(key).append("=").append(map.get(key)).append(";");
        }
        return sb.toString();
    }

}
