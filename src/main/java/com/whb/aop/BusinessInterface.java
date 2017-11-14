package com.whb.aop;

/**
 * 这是一个业务接口：给第三方模块调用的处理过程。
 */
public interface BusinessInterface {
    /**
     * @param username
     */
    public void dosomething(String username);
}