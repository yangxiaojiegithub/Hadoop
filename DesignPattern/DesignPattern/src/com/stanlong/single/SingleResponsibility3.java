package com.stanlong.single;

/**
 * 单一职责原则
 * @author Administrator
 *
 */
public class SingleResponsibility3 {

	public static void main(String[] args) {
		Vehicle2 vehicle = new Vehicle2();
		vehicle.run("汽车");
		vehicle.runAir("飞机");
		vehicle.runWater("轮船");
	}
}

// 方式3的分析
// 1、这种修改方法没有对原来的类做大的修改，只是增加方法
// 2、这里虽然没有在类的级别上遵守单一职责原则，但是在方法级别上仍然遵守单一职责原则
class Vehicle2{
	public void run(String vehicle){
		System.out.println(vehicle + " 在公路运行...");
	}
	public void runAir(String vehicle){
		System.out.println(vehicle + " 在天空运行...");
	}
	public void runWater(String vehicle){
		System.out.println(vehicle + " 在水中运行...");
	}
}
