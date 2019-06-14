package com.stanlong.l_web_annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestWebAnnotation {

	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/l_web_annotation/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		StudentAction studentAction = applicationContext.getBean("studentActionId", StudentAction.class);
		studentAction.execute();
	}
}
