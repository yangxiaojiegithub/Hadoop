package com.stanlong.i_spel;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpEL {

	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/i_spel/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		Customer customer = applicationContext.getBean("customerId", Customer.class);
		System.out.println(customer);
	}
}
