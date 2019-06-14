package com.stanlong.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器, 实现 HandlerExceptionResolver 接口
 * @author 矢量
 *
 */
public class CustomerExceptionResolver implements HandlerExceptionResolver{

	/**
	 * Exception ex 系统抛出的异常
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//handler 就是处理器适配器要执行的Handler对象
		CustomerException ce = null;
		if(ex instanceof CustomerException){
			ce = (CustomerException)ex;
		}else{
			ce = new CustomerException("未知错误");
		}
		//错误信息
		String message = ce.getMessage();
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", message);
		mv.setViewName("error");
		return mv;
	}

}
