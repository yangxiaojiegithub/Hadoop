package com.stanlong.e_scope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScope {

	
	/**
	 * Bean 的作用域
	 */
	@Test
	public void demo02(){
		String xmlPath = "com/stanlong/e_scope/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		BookService bookService2 = applicationContext.getBean("bookServiceId", BookService.class);
		
		System.out.println(bookService);
		System.out.println(bookService2);
	}
	
	
}
