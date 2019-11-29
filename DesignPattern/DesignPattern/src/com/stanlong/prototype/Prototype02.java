package com.stanlong.prototype;

/**
 * 原型模式
 * @author 矢量
 *
 * 2019年11月29日
 */
public class Prototype02{
	public static void main(String[] args) {
		Sheep sheep = new Sheep("tom", 1, "白色");
		Sheep sheep1 = (Sheep)sheep.clone();
		System.out.println(sheep);
		System.out.println(sheep1);
	}
}

// 使用原型模式完成对象的创建
class Sheep implements Cloneable{
	
	private String name;
	private int age;
	private String color;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Sheep(String name, int age, String color) {
		super();
		this.name = name;
		this.age = age;
		this.color = color;
	}
	@Override
	public String toString() {
		return "Sheep [name=" + name + ", age=" + age + ", color=" + color + "]";
	}
	// 克隆改实例，使用默认的 clone 方法来完成
	@Override
	protected Object clone(){
		Sheep sheep = null;
		try {
			sheep = (Sheep)super.clone();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sheep;
	}
	
}