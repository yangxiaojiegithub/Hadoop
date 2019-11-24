package com.stanlong.livkov;

/**
 * 里氏替换原则 -不正确版本
 * @author Administrator
 *
 */
public class Livkov {

	public static void main(String[] args) {
		A a = new A();
		System.out.println("11-3=" + a.func1(11, 3));
		System.out.println("1-8=" + a.func1(1, 8));
		
		System.out.println("-----------------------");
		
		B b = new B();
		System.out.println("11-3=" + b.func1(11, 3));    // 这里的本已是求 11-3的，结果计算成了 11+3
		System.out.println("1-8=" + b.func1(11, 3));
	}
}


class A{
	// 返回两个数的差
	public int func1(int num1, int num2){
		return num1 - num2;
	}
}

// B继承A
// 完成两个数相加，然后和9求和
class B extends A{
	
	// 这里重写了A类的方法
	public int func1(int a, int b){
		return a+b;
	}
	
	public int func2(int a, int b){
		return func1(a, b) + 9;
	}
}