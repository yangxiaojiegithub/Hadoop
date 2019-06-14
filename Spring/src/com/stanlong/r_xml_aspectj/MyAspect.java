package com.stanlong.r_xml_aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面类，含有多个通知
 * @author 矢量
 *
 */
public class MyAspect{

	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知: " + joinPoint.getSignature().getName());
	}
	
	public void myAfterReturning(JoinPoint joinPoint, Object ret){
		System.out.println("后置通知： " + joinPoint.getSignature().getName() + " -->" + ret);
	}
	
	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		//前
		System.out.println("前");
		//手动执行目标方法
		Object obj = proceedingJoinPoint.proceed();
		//后
		System.out.println("后");
		return obj;
	}
	
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知： " + e.getMessage());
	}
	
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}
}
