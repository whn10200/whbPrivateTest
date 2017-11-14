package com.whb.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author whb
 * @date 2017年11月14日 上午10:22:30 
 * @Description: （代理）调用处理器。<br>
 * 什么意思呢：当“代理者”被调用时，这个实现类中的invoke方法将被触发。<br>
 * “代理者”对象，外部模块/外部系统所调用的方法名、方法中的传参信息都将以invoke方法实参的形式传递到方法中。
 */
public class BusinessInvocationHandler implements InvocationHandler {

	/**
     * 真实的业务处理对象,此处的BusinessInterface可以换成Object类
     */
    private BusinessInterface realBusiness;

    public BusinessInvocationHandler(BusinessInterface realBusiness) {
        this.realBusiness = realBusiness;
    }
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("（代理）调用处理器被激活=====");
        System.out.println("“代理者对象”：" + proxy.getClass().getName());
        System.out.println("“外部模块/外部系统”调用的方法名：" + method.getName());
        
        //此处不对，会造成循环调用代理，自己代理自己了
        //System.out.println(proxy.toString());
        
        System.out.println("---------正式业务执行前；");
        Object resultObject = method.invoke(this.realBusiness, args);
        System.out.println("---------正式业务执行后；");

        return resultObject;
	}

}
