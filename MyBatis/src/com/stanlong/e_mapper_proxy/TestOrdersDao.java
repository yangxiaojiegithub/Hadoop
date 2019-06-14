package com.stanlong.e_mapper_proxy;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stanlong.pojo.OrdersCustomer;

public class TestOrdersDao {

	private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception{
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	}
	
	@Test
	public void testFindOrdersUser() throws Exception{
		OrdersDao ordersDao = applicationContext.getBean("ordersDao", OrdersDao.class);
		List<OrdersCustomer> olist = ordersDao.findOrdersUser();
		System.out.println(olist);
	}
}
