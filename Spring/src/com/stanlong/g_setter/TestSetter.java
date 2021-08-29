package com.stanlong.g_setter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSetter {

	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/g_setter/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		Person person = applicationContext.getBean("personId", Person.class);
		System.out.println(person);
	}
}
