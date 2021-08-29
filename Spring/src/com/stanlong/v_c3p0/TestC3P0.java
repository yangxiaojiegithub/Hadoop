package com.stanlong.v_c3p0;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestC3P0 {

	@Test
	public void demo01() {
		Tb_User tb_user = new Tb_User();
		tb_user.setUsername("zhangsansan");
		tb_user.setPassword("32221");
		tb_user.setId(3);
		
		String xmlPath = "com/stanlong/v_c3p0/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		
		//获得目标类
		TbUserDao tbUserDao = applicationContext.getBean("tbUserDaoId", TbUserDao.class);
		tbUserDao.updateUser(tb_user);
		
		List<Tb_User> userList = tbUserDao.findAll();
		for(Tb_User t_user : userList){
			System.out.println(t_user);
		}
		
	}
}
