package com.stanlong.zd_web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class HelloSpring extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从applicationContex作用域获得Spring容器
		//方式： 通过工具获取
		ApplicationContext applicationContext = 
		WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
		//操作
		AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 1000);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
