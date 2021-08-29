package com.stanlong.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 具体需求
// 看一个披萨的项目：要便于披萨种类的扩展，要便于维护
// 披萨的种类很多
// 披萨的制作有 prepare, bark, cut, box
// 完成披萨店的订购功能

/**
 * 工厂模式
 * 
 * @author Administrator
 * 缺点：违反了设计模式的ocp原则，即对扩展开放，对修改关闭。即当我们给类增加新的功能的时候。尽量不修改代码，或者尽肯能少修改代码
 * 在这个方法中如果新增加一个Pizza种类， OrderPizza 是需要跟着一起改的
 */
public class Factory01 {
	public static void main(String[] args) {
		//new OrderPizza();
	}
}

// 将 Pizza 设计成抽象类
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

// 订购Pizza
class OrderPizza {
	// 构造器
	public OrderPizza() {
		Pizza pizza = null;
		String orderType;
		do {
			orderType = getType();
			if (orderType.equals("greek")) {
				pizza = new GreekPizza();
			} else if (orderType.equals("cheese")) {
				pizza = new CheesPizza();
			} else {
				break;
			}
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
*/