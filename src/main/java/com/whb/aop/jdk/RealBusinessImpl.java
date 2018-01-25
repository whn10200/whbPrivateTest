package com.whb.aop.jdk;

/**
 * 这个类就是这个业务接口的真实实现。
 */
public class RealBusinessImpl implements BusinessInterface {

    @Override
    public void dosomething(String username) { 
        System.out.println("正在为用户：" + username + "，进行真实的业务处理。。。");
    }
}
