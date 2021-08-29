package com.stanlong.z_factory_transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {

	@Test
	public void demo01(){
		String xmlpath = "com/stanlong/z_factory_transaction/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		AccountService accountService = applicationContext.getBean("proxyAccountService", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 1000);
		
	}
}
