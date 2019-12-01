package com.stanlong.adapter;

/**
 * 适配器模式 --> 接口适配器模式
 * @author 矢量
 *
 * 2019年12月1日
 */
public class Adapter03 {
	public static void main(String[] args) {
		AbsAdapter absAdapter = new AbsAdapter(){
			// 只需要去覆盖我们需要使用的接口方法
			@Override
			public void m1() {
				System.out.println("使用了m1方法");
			}
		};
		
		absAdapter.m1();
	}
}


interface Interface4{
	public void m1();
	public void m2();
	public void m3();
	public void m4();
}

// 在 AbsAdapter中将 Interface4 的方法默认实现
abstract class AbsAdapter implements Interface4{
	
	public void m1(){
		
	}
	public void m2(){
		
	}
	public void m3(){
		
	}
	public void m4(){
		
	}
}
