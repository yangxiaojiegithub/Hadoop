package com.stanlong.d_factory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStaticFactory {

	/**
	 * 自定义实例工厂
	 */
	@Test
	public void demo01(){
		//1、创建工厂
		MyBeanFactory myBeanFactory = new MyBeanFactory();
		//2、通过工厂实例，获得对象
		 BookService bookService = myBeanFactory.createBookService(); 
		 bookService.addBook();
	}
	
	/**
	 * Spring 工厂
	 */
	@Test
	public void demo02(){
		String xmlPath = "com/stanlong/d_factory/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
	}
	
	
}
