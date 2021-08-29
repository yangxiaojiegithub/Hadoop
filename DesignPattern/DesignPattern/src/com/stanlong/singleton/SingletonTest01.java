package com.stanlong.singleton;

/**
 * 单例模式
 * @author Administrator
 *
 */
public class SingletonTest01 {
	public static void main(String[] args) {
		/*Singleton singleton1 = Singleton.getInstance();
		Singleton singleton2 = Singleton.getInstance();
		System.out.println(singleton1 == singleton2); // true
		System.out.println("singleton1.hashCode==>" + singleton1.hashCode());
		System.out.println("singleton2.hashCode==>" + singleton2.hashCode());*/
	}
}


// 饿汉式（静态常量）
// 优点：写法简单，在类装载时就完成了实例化。避免了线程同步问题
// 缺点：在类装载时就完成了实例化，没有达到 lazy loading的效果如果从始至终没有使用过这个实例，则会造成内存的浪费
/*class Singleton{
	// 构造器私有化，外部不能以new的方式实例化对象
	private Singleton(){
		
	}
	
	// 在类内部创建类的对象实例
	private final static Singleton instance = new Singleton();
	
	//提供一个公有的静态方法，返回实例对象
	public static Singleton getInstance(){
		return instance;
	}
}*/

//饿汉式（静态代码块）
/*class Singleton{
	// 构造器私有化，外部不能以new的方式实例化对象
	private Singleton(){
		
	}
	
	// 在类内部创建类的对象实例
	private static Singleton instance;
	
	// 在静态代码块中创建单例对象
	static{
		instance = new Singleton();
	}
	
	//提供一个公有的静态方法，返回实例对象
	public static Singleton getInstance(){
		return instance;
	}
}*/
