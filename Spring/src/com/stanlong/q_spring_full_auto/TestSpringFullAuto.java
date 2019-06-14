package com.stanlong.q_spring_full_auto;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringFullAuto {
	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/q_spring_full_auto/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = applicationContext.getBean("userServiceId", UserService.class);
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
		
	}

}
