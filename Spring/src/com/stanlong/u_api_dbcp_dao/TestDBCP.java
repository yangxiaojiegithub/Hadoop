package com.stanlong.u_api_dbcp_dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDBCP {

	@Test
	public void demo01() {
		Tb_User tb_user = new Tb_User();
		tb_user.setUsername("wangwuwu");
		tb_user.setPassword("123456");
		tb_user.setId(3);
		
		String xmlPath = "com/stanlong/u_api_dbcp_dao/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		
		//获得目标类
		TbUserDao tbUserDao = applicationContext.getBean("tbUserDaoId", TbUserDao.class);
		tbUserDao.updateUser(tb_user);
		
	}
}
