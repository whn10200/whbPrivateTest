package com.whb.utils;


import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 标的工具类 注意该工具类所有默认的金额单位为分 默认不保留小数
 * @author: 王艳兵
 * @date: 2017/9/14
 * @time: 15:58
 * @copyright: 拓道金服 Copyright (c) 2017
 */
public class BorrowUtils {
    /**
     * 清算开始时间 小时
     */
    private static final int START_TIME_HOUR = 23;

    /**
     * 清算开始时间 分钟
     */
    private static final int START_TIME_MINUTE = 55;

    /**
     * 清算结束时间 小时
     */
    private static final int END_TIME_HOUR = 0;

    /**
     * 清算结束时间 分钟
     */
    private static final int END_TIME_MINUTE = 5;
    /**
     * 期限单位:天
     */
    private static final int BORROW_TYPE_DAY = 0;

    /**
     * 期限单位:月
     */
    private static final int BORROW_TYPE_MONTH = 1;
    /**
     * 期限单位:年
     */
    private static final int BORROW_TYPE_YEAR = 2;



    /**
     * 等额本息获取收益
     * @param money  投资金额
     * @param period 投资期数 月
     * @param apr 收益 %;
     * @param type  期限类型:0:天 1:月 2:年
     * @return divisor
     */
    public static double fixedPaymentMortgage(double money,int period,double apr,int type){
        double monthApr = getMonthApr(apr,type);
        double monthMoney = getMonthMoney(monthApr,period,money);
        //剩余本金
        double residueMoney = money;
        //总利息
        double totalInterest = 0;

        for (int i = 0; i < period; i++){
            //利息
            double interest = 0 ;
            //本金
            double capital = 0;
            //最后一期
            if(i == (period - 1)){
                interest = BigDecimalUtils.mul(residueMoney,monthApr);
                capital = residueMoney;
            }else{
                //本期还款利息
                interest = BigDecimalUtils.mul(residueMoney ,monthApr);
                // 本期还款本金
                capital = BigDecimalUtils.sub(monthMoney,interest);
            }
            //剩余本金
            residueMoney = BigDecimalUtils.sub(residueMoney,capital);
            totalInterest = BigDecimalUtils.add(interest,totalInterest);
        }
        return BigDecimalUtils.round(totalInterest);
    }

    /**
     * 计算等额本息 :月还款=（本金*月利息*（1+月利率）^投资期限）/（（1+月利率）^投资期限-1）
     * @param monthApr 月利息
     * @param period 期数
     * @param money 金额
     * @return
     */
    private static double getMonthMoney(double monthApr,int period,double money){
        //公用的
        double common = Math.pow(BigDecimalUtils.add(1,monthApr,10),period);
        //除数
        double divisor = BigDecimalUtils.mul(money,monthApr,common);
        //被除数
        double dividend = BigDecimalUtils.sub(common , 1,10);
        //被除数为0 直接返回0
        if (dividend == 0){
            return 0D;
        }
        //月还款金额
        return BigDecimalUtils.round(BigDecimalUtils.div(divisor,dividend));
    }

    /**
     * 等额本息获取收益默认投资期限类型为月 :月还款=（本金*月利息*（1+月利率）^投资期限）/（（1+月利率）^投资期限-1）
     * @param money  投资金额
     * @param period 投资期数 月
     * @param apr 收益 %;
     * @return divisor
     */
    public static double fixedPaymentMortgage(double money,int period,double apr){
        return fixedPaymentMortgage(money,period,apr,BORROW_TYPE_MONTH);
    }
    /**
     * 获取月利息
     * @param apr 年化利息
     * @param type 期限类型:0:天 1:月 2:年
     * @return
     */
    private static double getMonthApr(double apr,int type){
        //转换为标准的利率
        apr = BigDecimalUtils.centToYuan(apr);
        if(BORROW_TYPE_DAY == type){
            return BigDecimalUtils.div(apr,365);
        }
        if(BORROW_TYPE_YEAR == type){
            return apr;
        }
        //月利息
        return BigDecimalUtils.div(apr,12);
    }


    /**
     * 按月付息 总收益
     * @param money 投标金额
     * @param period 投资期数
     * @param apr 利息 %
     * @param type   期限单位:0:天 1:月 2:年
     * @return
     */
    public static double perMonth(double money,int period,double apr,int type){
        double interest =  BigDecimalUtils.mul(getMonthApr(apr,type),money,period);
        return BigDecimalUtils.round(interest);
    }

    /**
     * 按月付息 总收益
     * @param money 投标金额
     * @param period 投资期数
     * @param apr 利息 %
     * @return
     */
    public static double perMonth(double money,int period,double apr){
        return perMonth(money,period,apr,BORROW_TYPE_MONTH);

    }


    /**
     * 等额本息生成回款计划
     * @param periods 总期数
     * @param money 投标金额
     * @param apr 基础利息
     * @param platformApr 平台加息
     * @param couponApr 加息券
     * @return
     */
    public static List<BorrowPlan> collectionFixedPaymentMortgage(int periods,double money,double apr,double platformApr,double couponApr,Date start){
        return collectionFixedPaymentMortgage(periods,money,apr,platformApr,couponApr,start,BORROW_TYPE_MONTH);
    }

    /**
     * 等额本息生成回款计划,回款时间以当天开始进行计算
     * @param periods 总期数
     * @param money 投标金额
     * @param apr 基础利息
     * @param platformApr 平台加息
     * @param couponApr 加息券
     * @param type 期限类型:0:天 1:月 2:年
     * @return
     */
    public static List<BorrowPlan> collectionFixedPaymentMortgage(int periods,double money,double apr,double platformApr,double couponApr,Date start,int type){
        //基础平台利息
        double baseMonthApr = getMonthApr(apr,type);
        //平台加息
        double platformMonthApr = getMonthApr(platformApr,type);
        //加息券
        double couponMonthApr = getMonthApr(couponApr,type);
        //月还款
        double baseMonthMoney = getMonthMoney(baseMonthApr,periods,money);
        //剩余本金
        double residueMoney = money;
        List<BorrowPlan> list = new ArrayList<>();
        BorrowPlan plan;
        for (int i = 1; i <= periods; i++){
            plan = new BorrowPlan();

            //基础收益
            double baseInterest = BigDecimalUtils.round(BigDecimalUtils.mul(residueMoney,baseMonthApr));
            //平台加息收益
            double platformInterest = BigDecimalUtils.round(BigDecimalUtils.mul(residueMoney,platformMonthApr));
            //加息券收益
            double couponInterest = BigDecimalUtils.round(BigDecimalUtils.mul(residueMoney,couponMonthApr));
            //本金
            double capital;
            //最后一期
            if(i == periods ){
                capital = residueMoney;
            }else{
                // 本期还款本金
                capital = BigDecimalUtils.sub(baseMonthMoney,baseInterest);
            }
            /**
             * 由于月还款金额四舍五入,在投标金额极小的时候,月还款金额会变大,
             * 因此每期还款本金可能会变大导致 最后一期或几期无剩余本金可还 因而出现负数
             */
            residueMoney = BigDecimalUtils.sub(residueMoney,capital);
            if (residueMoney < 0){
                residueMoney = 0;
            }

            //回款时间依次延续
            plan.setPreCollectionTime(getRepaymentDate(start,i,type));
            //预计回款月
            plan.setPreMonth(DateUtil.formatMin(plan.getPreCollectionTime()));
            plan.setPeriods(periods);
            //期数
            plan.setPeriod(i);
            //当期抵用券奖励
            plan.setPreCouponInterest(BigDecimal.valueOf(couponInterest));
            //当期平台加息奖励
            plan.setPrePlatformInterest(BigDecimal.valueOf(platformInterest));
            //当期回款本金
            plan.setPreCapital(BigDecimal.valueOf(capital));
            //当期总利息:基础利息+加息券利息+平台奖励利息
            double totalInterest = BigDecimalUtils.add(BigDecimalUtils.add(baseInterest,platformInterest),couponInterest);
            plan.setPreInterest(BigDecimal.valueOf(totalInterest));

            list.add(plan);
        }
        return list;
    }


    /**
     * 计算回款或还款时间
     * @param startTime 开始时间 默认当前日期
     * @param period 当前为第几期
     * @param periodUnit 期限单位
     * @return
     */
    private static Date getRepaymentDate(Date startTime,int period,int periodUnit){
        if(periodUnit == BORROW_TYPE_DAY){
            return DateUtil.addDays(startTime,period);
        }else if(periodUnit == BORROW_TYPE_MONTH){
            return DateUtil.addMonths(startTime,period);
        }else if(periodUnit == BORROW_TYPE_YEAR){
            return DateUtil.addYears(startTime,period);
        }
        return startTime;
    }

    /**
     * 按月付息生成回款信息列表
     * @param periods 总期数
     * @param money 投资金额
     * @param apr 基础利息
     * @param platformApr 平台加息
     * @param couponApr 个人加息券
     * @return
     */
    public static List<BorrowPlan> collectionPerMonth(int periods,double money,double apr,double platformApr,double couponApr,Date start){
        return collectionPerMonth(periods,money,apr,platformApr,couponApr,start,BORROW_TYPE_MONTH);
    }

    /**
     * 按月付息生成回款信息列表
     * @param periods 总期数
     * @param money 投资金额
     * @param apr 基础利息
     * @param platformApr 平台加息
     * @param couponApr 个人加息券
     * @param start  生成还款计划的开始时间
     * @param type 类型:0:按天计息 1:按月计息 2:按年计息
     * @return
     */
    public static List<BorrowPlan> collectionPerMonth(int periods,double money,double apr,double platformApr,double couponApr,Date start,int type){
        //基础平台利息
        double baseMonthApr = getMonthApr(apr,type);
        //平台加息
        double platformMonthApr = getMonthApr(platformApr,type);
        //加息券
        double couponMonthApr = getMonthApr(couponApr,type);

        /**
         * 由于生成回款记录时,总收益计算不一定等于每期回款收益相加总额 (四舍五入导致的)
         * 因此 最后一期需要额外计算
         */
        double baseTotal = perMonth(money, periods, apr, type);
        double platformTotal = perMonth(money, periods, platformApr, type);
        double couponTotal = perMonth(money, periods, couponApr, type);

        List<BorrowPlan> list = new ArrayList<>();
        BorrowPlan plan;
        for(int i = 1; i <= periods; i++){
            plan = new BorrowPlan();
            //最后一期回本金
            if(i == periods){
                plan.setPreCapital(BigDecimal.valueOf(money));
                plan.setPrePlatformInterest(BigDecimal.valueOf(platformTotal));
                plan.setPreCouponInterest(BigDecimal.valueOf(couponTotal));
                plan.setPreCollectionTime(getRepaymentDate(start,i,type));
                //预计回款月
                plan.setPreMonth(DateUtil.formatMin(plan.getPreCollectionTime()));
                double totalInterest = BigDecimalUtils.add(BigDecimalUtils.add(baseTotal,platformTotal),couponTotal);
                plan.setPreInterest(BigDecimal.valueOf(totalInterest));
                plan.setPeriod(i);
                plan.setPeriods(periods);
                list.add(plan);
            }else{
                //不回款本金
                plan.setPreCapital(BigDecimal.valueOf(0D));
                double platformInterest = BigDecimalUtils.round(BigDecimalUtils.mul(platformMonthApr,money));
                double couponInterest = BigDecimalUtils.round(BigDecimalUtils.mul(couponMonthApr,money));
                double baseInterest = BigDecimalUtils.round(BigDecimalUtils.mul(baseMonthApr,money));
                plan.setPrePlatformInterest(BigDecimal.valueOf(platformInterest));
                plan.setPreCouponInterest(BigDecimal.valueOf(couponInterest));
                plan.setPreCollectionTime(getRepaymentDate(start,i,type));
                //预计回款月
                plan.setPreMonth(DateUtil.formatMin(plan.getPreCollectionTime()));
                double totalInterest = BigDecimalUtils.add(BigDecimalUtils.add(baseInterest,platformInterest),couponInterest);

                plan.setPreInterest(BigDecimal.valueOf(totalInterest));
                plan.setPeriod(i);
                plan.setPeriods(periods);

                baseTotal = BigDecimalUtils.sub(baseTotal,baseInterest);
                platformTotal = BigDecimalUtils.sub(platformTotal,platformInterest);
                couponTotal = BigDecimalUtils.sub(couponTotal,couponInterest);

                list.add(plan);
            }
        }
        return list;
    }


    /**
     * 按天计息 总收益计算
     * @param money 金额 分
     * @param periods 投资天数
     * @param apr 利息 %
     * @return
     */
    public static double preDaily(double money,int periods,double apr){
        double interest =  BigDecimalUtils.mul(getMonthApr(apr,BORROW_TYPE_DAY),money,periods);
        return BigDecimalUtils.round(interest);
    }

    /**
     * 按天计息 期限单位自动无效 默认一期
     * @param periods  期限(天)
     * @param money 金额 分
     * @param apr 基础利息
     * @param platformApr 平台奖励
     * @param couponApr 加息券奖励
     * @return
     */
    public static List<BorrowPlan> collectionPreDaily(int periods,double money,double apr,double platformApr,double couponApr){
        double totalApr = BigDecimalUtils.add(BigDecimalUtils.add(apr, platformApr), couponApr);
        //总利息 = 平台加息+基础+加息券
        double interest = preDaily(money,periods,totalApr);
        //平台加息
        double platformInterest = preDaily(money,periods,platformApr);
        //加息券
        double couponInterest = preDaily(money,periods,couponApr);

        BorrowPlan borrowPlan = new BorrowPlan();
        borrowPlan.setPeriod(1);
        borrowPlan.setPeriods(1);
        borrowPlan.setPreInterest(BigDecimal.valueOf(interest));
        borrowPlan.setPreCouponInterest(BigDecimal.valueOf(couponInterest));
        borrowPlan.setPrePlatformInterest(BigDecimal.valueOf(platformInterest));
        borrowPlan.setPreCollectionTime(DateUtil.addDays(new Date(),periods));
        borrowPlan.setPreMonth(DateUtil.formatMin(borrowPlan.getPreCollectionTime()));
        borrowPlan.setPreCapital(BigDecimal.valueOf(money));

        return Lists.newArrayList(borrowPlan);
    }

//    /**
//     * 借款人
//     * 等额本息还款计划生成,还款时间默认以生成时间开始即当前时间依次延续一月
//     * @param periods 借款期限
//     * @param money 借款金额
//     * @param apr 利息:基础利息,额外加息等奖励均为平台自费
//     * @return
//     */
//
//    public static List<BorrowPlan> repaymentFixedPaymentMortgage(int periods,double money,double apr){
//        return repaymentFixedPaymentMortgage(periods,money,apr,BORROW_TYPE_MONTH);
//    }
//
//
//    /**
//     * 借款人
//     * 等额本息还款计划生成,还款时间默认以生成时间开始即当前时间依次延续一月
//     * @param periods 借款期限
//     * @param money 借款金额
//     * @param apr 利息:基础利息,额外加息等奖励均为平台自费
//     * @param type 期限类型:0:天 1:月 2:年
//     * @return
//     */
//
//    public static List<BorrowPlan> repaymentFixedPaymentMortgage(int periods,double money,double apr,int type){
//        double monthApr = getMonthApr(apr,type);
//        double monthMoney = getMonthMoney(monthApr,periods,money);
//        //剩余本金
//        double residueMoney = money;
//
//        List<BorrowPlan> list = new ArrayList<>();
//        BorrowPlan plan;
//        Date now = new Date();
//        for (int i = 1; i <= periods; i++){
//            //利息
//            double interest = 0 ;
//            //本金
//            double capital = 0;
//            //最后一期
//            if(i == periods){
//                interest = BigDecimalUtils.mul(residueMoney,monthApr);
//                capital = residueMoney;
//            }else{
//                //本期还款利息
//                interest = BigDecimalUtils.mul(residueMoney ,monthApr);
//                // 本期还款本金
//                capital = BigDecimalUtils.sub(monthMoney,interest);
//            }
//            //剩余本金
//            residueMoney = BigDecimalUtils.sub(residueMoney,capital);
//
//            plan = new BorrowPlan();
//            //总期数
//            plan.setPeriods(periods);
//            //当前期数
//            plan.setPeriod(i);
//            //还款时间
//            plan.setPreCollectionTime(getRepaymentDate(now,i,type));
//            //还款本金
//            plan.setPreCapital(capital);
//            //还款利息
//            plan.setPreInterest(interest);
//            list.add(plan);
//        }
//        return list;
//    }
//
//
//
//    /**
//     * 借款人按月付息生成还款计划
//     * @param periods 期限
//     * @param money 借款总额
//     * @param apr 利息
//     * @param type 期限类型:0:天 1:月 2:年
//     * @return
//     */
//
//    public static List<BorrowPlan> repaymentPerMonth(int periods,double money,double apr,int type){
//        //基础平台利息
//        double baseMonthApr = getMonthApr(apr,type);
//
//        List<BorrowPlan> list = new ArrayList<>();
//        BorrowPlan plan ;
//        Date now = new Date();
//        for(int i = 1; i <= periods; i++){
//            plan = new BorrowPlan();
//            //最后一期还本金
//            if(i == periods){
//                plan.setPreCapital(money);
//            }else{
//                //不还本金
//                plan.setPreCapital(0D);
//            }
//            plan.setPreInterest(BigDecimalUtils.mul(baseMonthApr,money));
//            //还款时间
//            plan.setPreCollectionTime(getRepaymentDate(now,i,type));
//            plan.setPeriod(i);
//            plan.setPeriods(periods);
//            list.add(plan);
//        }
//        return list;
//    }
//    /**
//     * 借款人按月付息生成还款计划
//     * @param periods 期限
//     * @param money 借款总额
//     * @param apr 利息
//     * @return
//     */
//
//    public static List<BorrowPlan> repaymentPerMonth(int periods,double money,double apr){
//        return repaymentPerMonth(periods,money,apr,BORROW_TYPE_MONTH);
//    }
//

    /**
     * 判断当前时间是否为清算时间
     * 默认 23:55:00-00:05:00 为清算时间
     * @return
     */
    public static boolean isClearingTime(){

        LocalTime now = LocalTime.now();
        LocalTime startTime = LocalTime.of(START_TIME_HOUR, START_TIME_MINUTE);
        LocalTime endTime = LocalTime.of(END_TIME_HOUR,END_TIME_MINUTE);

        if (now.isAfter(startTime) || now.isBefore(endTime)) {
            return true;
        }
        return false;
    }


    /**
     * 生成投标期限中文信息
     * @param period     期限
     * @param periodUnit 期限单位
     * @return
     */
    public static String getPeriodText(int period,int periodUnit){
        if(periodUnit == BORROW_TYPE_DAY){
            return period + "天";
        }else if(periodUnit == BORROW_TYPE_MONTH){
            return period + "个月";
        }else if(periodUnit == BORROW_TYPE_YEAR ){
            return period + "年";
        }
        return "";
    }

    public static Integer getPeriod(int period,int periodUnit) {
        if(periodUnit == BORROW_TYPE_DAY){
            return 1;
        }else if(periodUnit == BORROW_TYPE_MONTH){
            return period;
        }else if(periodUnit == BORROW_TYPE_YEAR ){
            return period * 12;
        }
        return 0;
    }

    /**
     * 获取计息日期
     * @param endTime
     * @return
     */
    public static Date getBearingTime(Date endTime,int period,int periodUnit){
        if (periodUnit == BORROW_TYPE_DAY){
            return DateUtil.addDays(endTime,-period);
        }else if(periodUnit == BORROW_TYPE_MONTH){
            return DateUtil.addMonths(endTime,-period);
        }else if(periodUnit == BORROW_TYPE_YEAR){
            return DateUtil.addYears(endTime,-period);
        }
        return new Date();
    }

    /**
     * 生成标的信息链接 供资金记录可点击 散标
     * @param productId
     * @return
     */
    public static String getBorrowUrl(Integer productId){
        return "/view/invest_detailScatter.html?productId=" + productId;
    }

    /**
     * 生成标的信息链接 供资金记录可点击 债权转让
     * @param productId
     * @return
     */
    public static String getTransferBorrowUrl(Integer productId){
        return "/view/invest_detailBond.html?productId=" + productId;
    }

    /**
     * 生成标的信息链接 供资金记录可点击  精选计划
     * @param productId
     * @return
     */
    public static String getPlanBorrowUrl(Integer productId){
        return "/view/invest_detailSift.html?productId=" + productId;
    }


    public static void main(String[] args) {
        List<BorrowPlan> list = collectionFixedPaymentMortgage(3,100000,10,0,0,new Date());
        list.forEach(borrowPlan -> System.out.println(borrowPlan));
        System.out.println(fixedPaymentMortgage(100000,3,10));;

        List<BorrowPlan> plans = collectionPerMonth(3,100000,10,0,0,new Date());
        plans.forEach(borrowPlan -> System.out.println(borrowPlan));

        System.out.println(perMonth(100000,3,10));;

        List<BorrowPlan> plans1 = collectionPreDaily(15, 10000, 10, 2, 0);
        plans1.forEach(borrowPlan -> System.out.println(borrowPlan));
    }
 
}
