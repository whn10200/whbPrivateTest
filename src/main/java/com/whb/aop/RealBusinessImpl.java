package com.whb.aop;

/**
 * 这个类就是这个业务接口的真实实现。
 */
public class RealBusinessImpl implements BusinessInterface {

    /* (non-Javadoc)
     * @see testDesignPattern.proxy.BusinessInterface#dosomething(java.lang.String)
     */
    @Override
    public void dosomething(String username) { 
        // 这里偷懒了一下，没有在工程中导入log4j的依赖。用System.out进行显示
        System.out.println("正在为用户：" + username + "，进行真实的业务处理。。。");
    }
}
