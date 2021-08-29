package com.stanlong.p_spring_half_auto;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringHalfAuto {
	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/p_spring_half_auto/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = applicationContext.getBean("proxyService", UserService.class);
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
		
	}

}
