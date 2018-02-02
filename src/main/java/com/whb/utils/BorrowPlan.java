package com.whb.utils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 生成还款计划回款计划
 * @author: 王艳兵
 * @date: 2017/9/18
 * @time: 17:59
 * @copyright: 拓道金服 Copyright (c) 2017
 */
public class BorrowPlan {

    /**
     * 期数
     */
    private int period;

    /**
     * 总期数
     */
    private int periods;

    /**
     * 预计回款时间 非交易中心使用
     */
    private Date preTime;

    /**
     * 预计回款月
     */
    private String preMonth;

    /**
     * 预计回款利息 总利息
     */
    private BigDecimal preInterest;

    /**
     * 预计回款本金
     */
    private BigDecimal preCapital;

    /**
     * 当期加息券收益
     */
    private BigDecimal preCouponInterest;

    /**
     * 平台加息收益
     */
    private BigDecimal prePlatformInterest;

    /**
     * 回款时间
     */
    private Date preCollectionTime;

    public String getPreMonth() {
        return preMonth;
    }

    public void setPreMonth(String preMonth) {
        this.preMonth = preMonth;
    }

    public Date getPreCollectionTime() {
        return preCollectionTime;
    }

    public void setPreCollectionTime(Date preCollectionTime) {
        this.preCollectionTime = preCollectionTime;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Date getPreTime() {
        return preTime;
    }

    public void setPreTime(Date preTime) {
        this.preTime = preTime;
    }

    public BigDecimal getPreInterest() {
        return preInterest;
    }

    public void setPreInterest(BigDecimal preInterest) {
        this.preInterest = preInterest;
    }

    public BigDecimal getPreCapital() {
        return preCapital;
    }

    public void setPreCapital(BigDecimal preCapital) {
        this.preCapital = preCapital;
    }

    public BigDecimal getPreCouponInterest() {
        return preCouponInterest;
    }

    public void setPreCouponInterest(BigDecimal preCouponInterest) {
        this.preCouponInterest = preCouponInterest;
    }

    public BigDecimal getPrePlatformInterest() {
        return prePlatformInterest;
    }

    public void setPrePlatformInterest(BigDecimal prePlatformInterest) {
        this.prePlatformInterest = prePlatformInterest;
    }

    @Override
    public String toString() {
        return "BorrowPlan{" +
                "period=" + period +
                ", periods=" + periods +
                ", preTime=" + preTime +
                ", preMonth='" + preMonth + '\'' +
                ", preInterest=" + preInterest +
                ", preCapital=" + preCapital +
                ", preCouponInterest=" + preCouponInterest +
                ", prePlatformInterest=" + prePlatformInterest +
                ", preCollectionTime=" + preCollectionTime +
                '}';
    }
}
