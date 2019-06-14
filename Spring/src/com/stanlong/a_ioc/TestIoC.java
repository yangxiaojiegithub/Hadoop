package com.stanlong.a_ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIoC {

	@Test
	public void demo01() {
		// 以前的写法
		UserService userService = new UserServiceImpl();
		userService.addUser();
	}

	@Test
	public void demo02() {
		// 新的写法
		// 1、获得Spring容器
		String xmlPath = "com/stanlong/a_ioc/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		// 2、由Spring 容器创建对象
		UserService userService = (UserService) applicationContext.getBean("userServiceId");
		userService.addUser();
	}
}
