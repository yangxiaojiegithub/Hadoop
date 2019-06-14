package com.stanlong.s_annotation_aspectj;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectJAnno {
	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/s_annotation_aspectj/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = applicationContext.getBean("userServiceId", UserService.class);
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
		
	}

}
