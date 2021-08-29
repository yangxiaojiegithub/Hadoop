package com.stanlong.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 抽象工厂模式
 * @author 矢量
 *
 * 2019年11月29日
 */
public class Factory04 {

	public static void main(String[] args) {
		//new OrderPizza(new BJFactory());
		new OrderPizza(new LDFactory());
	}
}

// 一个抽象工厂模式的抽象层（接口）
interface AbsFactory{
	public Pizza createPizza(String orderType);
}


abstract class Pizza {
	protected String name;

	// 准备原材料， 不同的Pizza不一样，因此改方法设计成抽象方法
	public abstract void prepare();

	public void bake() {
		System.out.println(name + " baking;");
	}

	public void cut() {
		System.out.println(name + " cutting;");
	}

	public void box() {
		System.out.println(name + " boxing;");
	}

	public void setName(String name) {
		this.name = name;
	}
}

class BJFactory implements AbsFactory{

	@Override
	public Pizza createPizza(String orderType) {
		Pizza pizza = null;
		if(orderType.equals("cheese")){
			pizza = new BJCheesePizza();
		}else if(orderType.equals("pepper")){
			pizza = new BJPepperPizza();
		}
				
		return pizza;
	}
}

class LDFactory implements AbsFactory{

	@Override
	public Pizza createPizza(String orderType) {
		Pizza pizza = null;
		if(orderType.equals("cheese")){
			pizza = new LDCheesePizza();
		}else if(orderType.equals("pepper")){
			pizza = new LDPepperPizza();
		}
				
		return pizza;
	}
}

class OrderPizza {
	
	// 定义一个抽象方法，让各个工厂子类自己实现
	AbsFactory factory;
	
	public OrderPizza(AbsFactory factory){
		setFactory(factory);
	}
	
	
	private void setFactory(AbsFactory factory){
		Pizza pizza = null;
		String orderType = "";
		this.factory = factory;
		do {
			orderType = getType();
			pizza = factory.createPizza(orderType);
			if(pizza != null){
				pizza.setName(orderType);
				pizza.prepare();
				pizza.bake();
				pizza.cut();
				pizza.box();
			}else{
				System.out.println("订购失败");
				break;
			}
		} while (true);
	}
		
	private String getType(){
		try{
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("input pizza type:");
			String str = strin.readLine();
			return str;
		}catch(IOException e){
			e.printStackTrace();
			return "";
		}
	}
}

class BJCheesePizza extends Pizza{
	@Override
	public void prepare() {
		System.out.println("给 BJCheesePizza 准备原材料 ");
	}
}

class BJPepperPizza extends Pizza{
	@Override
	public void prepare() {
		System.out.println("给 BJPepperPizza 准备原材料 ");
	}
}

class LDCheesePizza extends Pizza{
	@Override
	public void prepare() {
		System.out.println("给 LDCheesePizza 准备原材料 ");
	}
}

class LDPepperPizza extends Pizza{
	@Override
	public void prepare() {
		System.out.println("给 LDPepperPizza 准备原材料 ");
	}
}