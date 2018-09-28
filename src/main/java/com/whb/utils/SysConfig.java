package com.whb.utils;

/**
 * @author whb
 * @date 2018年9月28日 下午3:28:25 
 * @Description: 全局系统参数
 */
public class SysConfig {

    /**
     * 获取系统参数的值
     * @param key
     * @return
     */
    public static String getValue(String key){
        return key;
    }

    /**
     * 获取系统参数值
     * @param key
     * @return
     */
    public static double getDouble(String key){
        String value = getValue(key);
        if(value != null){
            return Double.parseDouble(value);
        }
        return 0D;
    }
}
