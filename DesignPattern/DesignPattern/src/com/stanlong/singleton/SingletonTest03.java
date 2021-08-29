package com.stanlong.singleton;

/**
 * 单例模式
 * @author Administrator
 *
 */
public class SingletonTest03 {
	public static void main(String[] args) {
		/*Singleton singleton1 = Singleton.getInstance();
		Singleton singleton2 = Singleton.getInstance();
		System.out.println(singleton1 == singleton2); // true
		System.out.println("singleton1.hashCode==>" + singleton1.hashCode());
		System.out.println("singleton2.hashCode==>" + singleton2.hashCode());*/
	}

}

// 双重检查
// 推荐使用
/*class Singleton{
	private Singleton(){
		
	}
	
	private static volatile Singleton instance;
	
	// 提供一个静态的公有方法，加入双重检查的代码，解决线程安全问题，同时解决懒加载问题,也保证了效率的问题
	public static Singleton getInstance(){
		if(instance == null){
			synchronized (Singleton.class) {
				if(instance == null){      //两次if判断，保证线程安全
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}*/