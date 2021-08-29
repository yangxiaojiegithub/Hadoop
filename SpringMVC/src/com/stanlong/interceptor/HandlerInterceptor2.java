package com.stanlong.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HandlerInterceptor2 implements HandlerInterceptor{

	//进入Handler方法前执行
	//用于身份认证，身份授权
	//比如身份认证
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception{
		//表示拦截住，不向下执行
		return true;
	}
	
	//进入handler方法之后 ，在返回 modelAndView 之前
	//应用场景从 modelAndView 出发，将公用的模型数据在这里传到视图，也可以统一指定视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception{
	}
	
	//执行handler方法之后，执行此方法
	//统一的异常处理，统一的日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object hander, Exception ex){
	}
}
