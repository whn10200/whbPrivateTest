package com.whb.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author wuchengjie
 * @Date 2017/12/4 0004 15:58
 * @Introduction 银行存管订单号加一
 */
public class BankOrderNoUtils {



    public  static String increaseTimes(String oldOrderNo){

        if(StringUtils.isBlank(oldOrderNo))
        {
            return null;
        }
        int index = oldOrderNo.lastIndexOf("_");
        if(index<=0){
            return null;
        }
        String headStr = oldOrderNo.substring(0,index+1);
        String timesStr = oldOrderNo.substring(index+1);
        Integer timesInt = Integer.parseInt(timesStr);

        timesInt++;
        String timesNewStr = String.valueOf(timesInt);

        return headStr+timesNewStr;
    }

}
