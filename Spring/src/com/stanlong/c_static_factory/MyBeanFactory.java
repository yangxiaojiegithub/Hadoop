package com.stanlong.c_static_factory;

public class MyBeanFactory {

	/*
	 * 创建静态工厂
	 */
	public static BookService createBookService(){
		return new BookServiceImpl();
	}
}
