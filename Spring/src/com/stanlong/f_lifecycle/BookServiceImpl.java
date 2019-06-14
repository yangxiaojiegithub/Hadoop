package com.stanlong.f_lifecycle;

public class BookServiceImpl implements BookService{


	@Override
	public void addBook() {
		System.out.println("f_lifecycle addBook()");
	}
	
	public void myInit(){
		System.out.println("初始化方法");
	}
	
	public void myDestroy(){
		System.out.println("销毁方法");
	}
}
