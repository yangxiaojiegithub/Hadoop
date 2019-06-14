package com.stanlong.s_annotation_aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面类，含有多个通知
 * @author 矢量
 *
 */
@Component
@Aspect
public class MyAspect{

	//@Before("execution(* com.stanlong.s_annotation_aspectj.UserServiceImpl.*(..))")
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知: " + joinPoint.getSignature().getName());
	}
	
	//声明公共的切入点表达示
	@Pointcut("execution(* com.stanlong.s_annotation_aspectj.UserServiceImpl.*(..))")
	public void myPointCut(){
		
	}
	
	//@AfterReturning(value="myPointCut()", returning="ret")
	public void myAfterReturning(JoinPoint joinPoint, Object ret){
		System.out.println("后置通知： " + joinPoint.getSignature().getName() + " -->" + ret);
	}
	
	//@Around(value="myPointCut()")
	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		//前
		System.out.println("前");
		//手动执行目标方法
		Object obj = proceedingJoinPoint.proceed();
		//后
		System.out.println("后");
		return obj;
	}
	
	//@AfterThrowing(value="execution(* com.stanlong.s_annotation_aspectj.UserServiceImpl.*(..))", throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知： " + e.getMessage());
	}
	
	@After("myPointCut()")
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}
}
