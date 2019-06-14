package com.stanlong.d_factory;

public class MyBeanFactory {

	/*
	 * 创建实例工厂，非静态
	 */
	public BookService createBookService(){
		return new BookServiceImpl();
	}
}
