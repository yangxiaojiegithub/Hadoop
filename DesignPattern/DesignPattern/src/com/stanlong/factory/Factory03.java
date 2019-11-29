package com.stanlong.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 工厂方法模式
 * @author Administrator
 *
 */
public class Factory03 {
	public static void main(String[] args) {
		/*String local = "bj";
		if(local.equals("bj")){
			new BJOrderPizza();
		}else{
			new LDOrderPizza();
		}*/
	}
}

/*abstract class Pizza {
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


abstract class OrderPizza {
	
	// 定义一个抽象方法，让各个工厂子类自己实现
	abstract Pizza createPizza(String orderType);
	
	public OrderPizza(){
		Pizza pizza = null;
		String orderType = ""; // 用户输入
		do {
			orderType = getType();
			pizza = createPizza(orderType);  // 抽象方法，由工厂子类完成
			pizza.setName(orderType);
			pizza.prepare();
			pizza.bake();
			pizza.cut();
			pizza.box();
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

class BJOrderPizza extends OrderPizza{

	@Override
	Pizza createPizza(String orderType) {
		Pizza pizza = null;
		if(orderType.equals("cheese")){
			pizza = new BJCheesePizza();
		}else if(orderType.equals("pepper")){
			pizza = new BJPepperPizza();
		}
		return pizza;
	}
}

class LDOrderPizza extends OrderPizza{

	@Override
	Pizza createPizza(String orderType) {
		Pizza pizza = null;
		if(orderType.equals("cheese")){
			pizza = new BJCheesePizza();
		}else if(orderType.equals("pepper")){
			pizza = new BJPepperPizza();
		}
		return pizza;
	}
	
}*/