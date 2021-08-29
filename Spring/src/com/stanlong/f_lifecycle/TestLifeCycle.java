package com.stanlong.f_lifecycle;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLifeCycle {

	
	/**
	 * Bean 的生命周期
	 */
	@Test
	public void demo01() throws Exception{
		String xmlPath = "com/stanlong/f_lifecycle/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
		
		//调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
		//关闭容器的两种方法：第一种
		applicationContext.getClass().getMethod("close").invoke(applicationContext);
	}
	
	@Test
	public void demo02() throws Exception{
		String xmlPath = "com/stanlong/f_lifecycle/applicationContext.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
		
		//调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
		//关闭容器的两种方法：第二种
		applicationContext.close();
	}
	
	
}
