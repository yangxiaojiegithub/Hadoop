package com.stanlong.h_p_name;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestP {

	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/h_p_name/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		Person person = applicationContext.getBean("personId", Person.class);
		System.out.println(person);
	}
}
