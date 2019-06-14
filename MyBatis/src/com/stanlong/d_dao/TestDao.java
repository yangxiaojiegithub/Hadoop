package com.stanlong.d_dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stanlong.pojo.User;

public class TestDao {

	/**
	 * 此方法在执行测试方法之前执行
	 * @throws Exception
	 */
	private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception{
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	}
	
	@Test
	public void testFindUserById() throws Exception{
		UserDao userDao = (UserDao) applicationContext.getBean("userDao");
		User user = userDao.findUserById(36);
		System.out.println(user);
		
	}
}
