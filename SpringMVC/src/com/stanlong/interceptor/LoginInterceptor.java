package com.stanlong.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器
 * @author 矢量
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception{
		//获取请求的url
		String url = request.getRequestURI();
		//判断 url是否是公开地址， 这里的公开地址是登陆提交的地址
		if(url != null && url.indexOf("login.action")>=0){
			//如果进行登录提交， 放行
			return true;
		}
		//判断 session
		HttpSession session = request.getSession();
		//从session中取出用户信息
		String username = (String)session.getAttribute("username");
		if(username != null){ //表示用户身份存在，放行
			return true;
		}
		
		//到这里表示用户身份需要认证，跳转到登录页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		return false;
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
