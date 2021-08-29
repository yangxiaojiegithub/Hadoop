package com.stanlong.c_static_factory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStaticFactory {

	/**
	 * 自定义静态工厂
	 */
	@Test
	public void demo01(){
		 BookService bookService = MyBeanFactory.createBookService();
		 bookService.addBook();
	}
	
	/**
	 * Spring 工厂
	 */
	@Test
	public void demo02(){
		String xmlPath = "com/stanlong/c_static_factory/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookSerivceId", BookService.class);
		bookService.addBook();
	}
	
	
}
