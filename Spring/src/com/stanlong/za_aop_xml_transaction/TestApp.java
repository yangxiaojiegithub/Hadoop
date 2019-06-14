package com.stanlong.za_aop_xml_transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {

	@Test
	public void demo01(){
		String xmlpath = "com/stanlong/za_aop_xml_transaction/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 1000);
		
	}
}
