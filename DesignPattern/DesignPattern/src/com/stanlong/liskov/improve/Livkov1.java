package com.stanlong.liskov.improve;

/**
 * 里氏替换原则
 * @author Administrator
 *
 */
public class Livkov1 {

	public static void main(String[] args) {
		A a = new A();
		System.out.println("11-3=" + a.func1(11, 3));
		System.out.println("1-8=" + a.func1(1, 8));
		
		System.out.println("-----------------------");
		
		// 因为B类不再继承A类，因此调用者不在会认为func1是求减法
		// 调用完成的功能会很明确
		B b = new B();
		System.out.println("11+3=" + b.func1(11, 3));    
		System.out.println("1+8=" + b.func1(8, 3));
		
		// 使用组合仍然可以使用到A类的方法
		System.out.println("11-3=" + b.func3(11, 8));
	}
}
// 创建一个更加基础的几类
class Base{
	// 把更加基础的方法写道 Base 类
	
}

class A extends Base{
	// 返回两个数的差
	public int func1(int num1, int num2){
		return num1 - num2;
	}
}

class B extends Base{

	// 如果B需要使用A类的方法，使用组合关系
	private A a = new A();
	
	public int func1(int a, int b){
		return a+b;
	}
	
	public int func2(int a, int b){
		return func1(a, b) + 9;
	}
	
	// 我们仍使用A类中的方法
	public int func3(int a, int b){
		return this.a.func1(a, b);
	}
}