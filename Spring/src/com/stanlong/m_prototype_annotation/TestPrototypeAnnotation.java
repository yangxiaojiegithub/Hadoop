package com.stanlong.m_prototype_annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPrototypeAnnotation {

	
	@Test
	public void demo02(){
		String xmlPath = "com/stanlong/m_prototype_annotation/applicationContext.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		//BookService bookService2 = applicationContext.getBean("bookServiceId", BookService.class);
		
		System.out.println(bookService);
		//System.out.println(bookService2);
		applicationContext.close();
	}
	
	
}
