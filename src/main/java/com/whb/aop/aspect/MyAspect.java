package com.whb.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspect {

	@Before("execution(* cn.itcast.spring3.demo1.UserDao.add(..))")
	public void before(JoinPoint joinPoint) {
		System.out.println("前置增强...." + joinPoint);
	}

	@AfterReturning(value = "execution(* cn.itcast.spring3.demo1.UserDao.update(..))", returning = "returnVal")
	public void afterReturin(Object returnVal) {
		System.out.println("后置增强....方法的返回值:" + returnVal);
	}

	@Around(value = "MyAspect.myPointcut()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		System.out.println("环绕前增强....");
		Object obj = proceedingJoinPoint.proceed();
		System.out.println("环绕后增强....");
		return obj;
	}

	@AfterThrowing(value = "MyAspect.myPointcut()", throwing = "e")
	public void afterThrowing(Throwable e) {
		System.out.println("不好了 出异常了!!!" + e.getMessage());
	}

	@After("MyAspect.myPointcut()")
	public void after() {
		System.out.println("最终通知...");
	}

	@Pointcut("execution(* cn.itcast.spring3.demo1.UserDao.find(..))") // 切点的定义
	private void myPointcut() {
	}

}
