package com.stanlong.k_annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {

	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/k_annotation/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = applicationContext.getBean("userServiceId", UserService.class);
		userService.addUser();
	}
}
