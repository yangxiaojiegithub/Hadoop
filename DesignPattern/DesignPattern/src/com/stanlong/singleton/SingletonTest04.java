package com.stanlong.singleton;

/**
 * 单例模式
 * @author Administrator
 *
 */
public class SingletonTest04 {
	public static void main(String[] args) {
		/*Singleton singleton1 = Singleton.getInstance();
		Singleton singleton2 = Singleton.getInstance();
		System.out.println(singleton1 == singleton2); // true
		System.out.println("singleton1.hashCode==>" + singleton1.hashCode());
		System.out.println("singleton2.hashCode==>" + singleton2.hashCode());*/
	}
}

// 静态内部类
// 采用类装载机制，保证初始化实例时只有一个线程
// 静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载 SingletonInstance类，
// 从而完成 Singleton的实例化
// 类的静态属性只会在第一次加载类的时候初始化，保证了线程安全
// 优点：避免了线程不安全，利用静态内部类的特点实现延迟加载，效率高
// 推荐使用
/*class Singleton{
	private Singleton(){
		
	}
	
	// 写一个静态内部类，改类中有一个静态属性
	private static class SingletonInstance{
		private static final Singleton INSTANCE = new Singleton();
	}
	
	// 提供一个静态的公有方法，直接返回 SingletonInstance.INSTANCE
	public static Singleton getInstance(){
		return SingletonInstance.INSTANCE;
	}
}*/