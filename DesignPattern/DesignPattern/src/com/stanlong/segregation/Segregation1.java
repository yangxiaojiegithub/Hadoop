package com.stanlong.segregation;

/**
 * 不遵守接口隔离原则， B类和D类 必须要实现不需要的方法
 * @author Administrator
 *
 */
public class Segregation1 {

	public static void main(String[] args) {
		A a = new A();               // A类通过接口依赖B类
		a.depend1(new B());
		a.depend2(new B());
		a.depend3(new B());
		
		C c = new C();              // C类通过接口依赖D类
		c.depend1(new D());
		c.depend4(new D());
		c.depend5(new D());
	}
}

interface Interface1{
	void operation1();
	void operation2();
	void operation3();
	void operation4();
	void operation5();
}

class B implements Interface1{

	@Override
	public void operation1() {
		System.out.println("B 中实现了 operation1");
	}

	@Override
	public void operation2() {
		System.out.println("B 中实现了 operation2");
		
	}

	@Override
	public void operation3() {
		System.out.println("B 中实现了 operation3");
		
	}

	@Override
	public void operation4() {
		System.out.println("B 中实现了 operation4");
		
	}

	@Override
	public void operation5() {
		System.out.println("B 中实现了 operation5");
		
	}
}

class D implements Interface1{
	@Override
	public void operation1() {
		System.out.println("D 中实现了 operation1");
	}

	@Override
	public void operation2() {
		System.out.println("D 中实现了 operation2");
		
	}

	@Override
	public void operation3() {
		System.out.println("D 中实现了 operation3");
		
	}

	@Override
	public void operation4() {
		System.out.println("D 中实现了 operation4");
		
	}

	@Override
	public void operation5() {
		System.out.println("D 中实现了 operation5");
		
	}
}

class A { // A 类通过接口Interface1 依赖（使用）B类，但只会用到1，2，3方法
	public void depend1(Interface1 i){
		i.operation1();
	}
	public void depend2(Interface1 i){
		i.operation2();
	}
	public void depend3(Interface1 i){
		i.operation3();
	}
}

class C{ // A 类通过接口Interface1 依赖（使用）D类，但只会用到1，4，5方法
	public void depend1(Interface1 i){
		i.operation1();
	}
	public void depend4(Interface1 i){
		i.operation4();
	}
	public void depend5(Interface1 i){
		i.operation5();
	}
}