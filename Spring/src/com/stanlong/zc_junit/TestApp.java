package com.stanlong.zc_junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/stanlong/zc_junit/applicationContext.xml")
public class TestApp {

	@Autowired //与 Junit整合，不需要在Spring.xml中配置扫描
	private AccountService accountService;

	@Test
	public void demo01(){
		//String xmlpath = "com/stanlong/zb_annotiation_transaction/applicationContext.xml";
		//ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		//AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 1000);
		
	}
}
