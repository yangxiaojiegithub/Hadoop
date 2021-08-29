package com.stanlong.y_transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {

	@Test
	public void demo01(){
		String xmlpath = "com/stanlong/y_transaction/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 500);
		
	}
}
