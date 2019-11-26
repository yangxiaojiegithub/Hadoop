package com.stanlong.singleton;

/**
 * 单例模式
 * @author Administrator
 *
 */
public class SingletonTest02 {
	public static void main(String[] args) {
		/*Singleton singleton1 = Singleton.getInstance();
		Singleton singleton2 = Singleton.getInstance();
		System.out.println(singleton1 == singleton2); // true
		System.out.println("singleton1.hashCode==>" + singleton1.hashCode());
		System.out.println("singleton2.hashCode==>" + singleton2.hashCode());*/
	}

}

// 懒汉式（线程不安全）
//缺点：只能在单线程下使用，在实际开发中不要使用这种方式
/*class Singleton{
	private Singleton(){
		
	}
	
	private static Singleton instance;
	
	// 提供一个静态的公有方法，当使用改方法时，采取创建 instance
	public static Singleton getInstance(){
		if(instance == null){
			instance = new Singleton();
		}
		return instance;
	}
}*/

//懒汉式（线程安全， 同步方法）
// 缺点：效率低，不推荐使用
/*class Singleton{
	private Singleton(){
		
	}
	
	private static Singleton instance;
	
	// 提供一个静态的公有方法，当使用改方法时，采取创建 instance。加入同步处理的代码，解决线程安全问题
	public static synchronized Singleton getInstance(){
		if(instance == null){
			instance = new Singleton();
		}
		return instance;
	}
}*/

//懒汉式（ 同步代码块）
// 不能再开发中使用
/*class Singleton{
	private Singleton(){
		
	}
	
	private static Singleton instance;
	
	// 提供一个静态的公有方法，当使用改方法时，采取创建 instance。加入同步处理的代码，解决线程安全问题
	public static Singleton getInstance(){
		if(instance == null){
			synchronized (Singleton.class) { // 这样写时有问题的，连线程安全都保证不了
				instance = new Singleton();
			}
		}
		return instance;
	}
}*/