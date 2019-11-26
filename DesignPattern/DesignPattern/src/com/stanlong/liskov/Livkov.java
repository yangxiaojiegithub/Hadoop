package com.stanlong.liskov;

/**
 * �����滻ԭ�� -����ȷ�汾
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
		System.out.println("11-3=" + b.func1(11, 3));    // ����ı������� 11-3�ģ����������� 11+3
		System.out.println("1-8=" + b.func1(11, 3));
	}
}


class A{
	// �����������Ĳ�
	public int func1(int num1, int num2){
		return num1 - num2;
	}
}

// B�̳�A
// �����������ӣ�Ȼ���9���
class B extends A{
	
	// ������д��A��ķ���
	public int func1(int a, int b){
		return a+b;
	}
	
	public int func2(int a, int b){
		return func1(a, b) + 9;
	}
}