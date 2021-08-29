package com.stanlong.m_prototype_annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("bookServiceId")
//@Scope("prototype")
public class BookServiceImpl implements BookService{


	@Override
	public void addBook() {
		System.out.println("m_prototype_annotation addBook()");
	}
	
	@PostConstruct
	public void myInit(){
		System.out.println("初始化方法");
	}
	
	@PreDestroy
	public void myDestroy(){
		System.out.println("销毁方法");
	}
}
