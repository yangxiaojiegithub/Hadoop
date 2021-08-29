package com.stanlong.builder;

/**
 * 建造者模式
 * @author 矢量
 * 
 * 2019年11月30日
 */
public class Builder01 {
	
	public static void main(String[] args) {
		/*CommonHouse commonHouse = new CommonHouse();
		commonHouse.build();*/
	}
	
}

// 缺点：没有设计缓存层对象，不方便程序的扩展和维护
/*abstract class AbstractHouse{
	// 打地基
	public abstract void buildBasic();
	
	//砌墙
	public abstract void buildWalls();
	
	// 封顶
	public abstract void roofed();
	
	public void build(){
		buildBasic();
		buildWalls();
		roofed();
	}
}

class CommonHouse extends AbstractHouse{

	@Override
	public void buildBasic() {
		System.out.println("给普通房子打地基");
		
	}

	@Override
	public void buildWalls() {
		System.out.println("给普通房子打砌墙");
		
	}

	@Override
	public void roofed() {
		System.out.println("给普通房子打封顶");
	}
}*/