package com.stanlong.prototype;

/**
 * 原型模式
 * @author 矢量
 * 现在又一只羊，姓名：tom， 年龄：1， 颜色：白色。需求，创建和 tom 羊属性完全相同的10只羊
 * 2019年11月29日
 */
public class Prototype01 {
	public static void main(String[] args) {
		/*Sheep sheep = new Sheep("tom", 1, "白色");
		Sheep sheep2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
		// ...
		System.out.println(sheep);
		System.out.println(sheep2);
		// ...
*/	}
}

// 缺点：再创建新的对象时，总是需要重新获取原始对象的属性，如果创建的对象比较复杂时，效率低
// 总是需要重新初始化对象，而不是动态的获得对象的运行时状态，不够灵活
/*class Sheep{
	
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
}*/
