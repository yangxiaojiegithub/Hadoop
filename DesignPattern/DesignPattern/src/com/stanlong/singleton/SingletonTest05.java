package com.stanlong.singleton;

/**
 * 单例模式
 * @author Administrator
 *
 */
public class SingletonTest05 {
	public static void main(String[] args) {
		Singleton singleton1 = Singleton.INSTANCE;
		Singleton singleton2 = Singleton.INSTANCE;
		System.out.println(singleton1 == singleton2); // true
		System.out.println("singleton1.hashCode==>" + singleton1.hashCode());
		System.out.println("singleton2.hashCode==>" + singleton2.hashCode());
	}
}

// 枚举
// 优点：不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象
// 推荐使用
enum Singleton{
	
	INSTANCE; // 属性
	
	public void sayOk(){
		System.out.println("ok");
	}
	
	
}