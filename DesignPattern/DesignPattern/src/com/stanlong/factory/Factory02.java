package com.stanlong.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 简单工厂模式
 * @author Administrator
 *
 */
public class Factory02 {
	public static void main(String[] args) {
		//new OrderPizza(new SimpleFactory());
		
		// 静态工厂调用
		// new OrderPizza2();
	}
}

// 简单工厂类, 当有新的Pizza进来时，只修改 SimpleFactory 即可
/*class SimpleFactory{
	// 根据 orderType 返回对应的pizza对象
	public Pizza createPizza(String orderType){
		Pizza pizza = null;
		if (orderType.equals("greek")) {
			pizza = new GreekPizza();
		} else if (orderType.equals("cheese")) {
			pizza = new CheesPizza();
		} else if (orderType.equals("china")) {
			pizza = new ChinaPizza();
		} 
		return pizza;
	}
	
	// 简单工厂模式也叫静态工厂模式
	public static Pizza createPizza2(String orderType){
		Pizza pizza = null;
		if (orderType.equals("greek")) {
			pizza = new GreekPizza();
		} else if (orderType.equals("cheese")) {
			pizza = new CheesPizza();
		} else if (orderType.equals("china")) {
			pizza = new ChinaPizza();
		} 
		return pizza;
	}
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

class CheesPizza extends Pizza {

	@Override
	public void prepare() {
		System.out.println("给制作奶酪Pizza准备材料");
	}
}

class GreekPizza extends Pizza {

	@Override
	public void prepare() {
		System.out.println("给制作希腊Pizza准备材料");
	}
}

// 新增一个pizza
class ChinaPizza extends Pizza{
	@Override
	public void prepare() {
		System.out.println("给制作ChinaPizza准备材料");
	}
}

class OrderPizza {
	
	// 定义一个简单工厂对象 
	SimpleFactory simpleFactory = new SimpleFactory();
	Pizza pizza = null;
	
	public OrderPizza(SimpleFactory simpleFactory){
		setFactory(simpleFactory);
	}
	
	public void setFactory(SimpleFactory simpleFactory){
		String orderType = ""; // 用户输入
		this.simpleFactory = simpleFactory; // 设置简单工厂对象
		do {
			orderType = getType();
			pizza = this.simpleFactory.createPizza(orderType);
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

class OrderPizza2 {
	
	// 定义一个简单工厂对象 
	String orderType = "";
	Pizza pizza = null;
	
	public OrderPizza2(){
		do {
			orderType = getType();
			pizza = SimpleFactory.createPizza2(orderType); // 静态工厂
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
}*/