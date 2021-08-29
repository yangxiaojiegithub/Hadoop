package com.stanlong.builder;

/**
 * 建造者模式
 * @author 矢量
 *
 * 2019年11月30日
 */
public class Builder02 {
	public static void main(String[] args) {
		// 盖普通房子
		CommonHouse commonHouse = new CommonHouse();
		
		// 准备创建房子的指挥者
		HouseDirector houseDirector = new HouseDirector(commonHouse);
		
		// 完成盖房子，返回产品
		houseDirector.constructHouse();
		
		System.out.println("--------------------------------------------------");
		
		// 盖高楼
		HighBuilding highBuilding = new HighBuilding();
		houseDirector.setHouseBuilder(highBuilding);
		houseDirector.constructHouse();
	}
}


class House{
	private String basic;
	private String wall;
	private String roofed;
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	public String getWall() {
		return wall;
	}
	public void setWall(String wall) {
		this.wall = wall;
	}
	public String getRoofed() {
		return roofed;
	}
	public void setRoofed(String roofed) {
		this.roofed = roofed;
	}
}

abstract class HouseBuilder{
	protected House house = new House();
	
	// 将建造的流程写好, 抽象方法
	// 打地基
	public abstract void buildBasic();
	
	//砌墙
	public abstract void buildWalls();
	
	// 封顶
	public abstract void roofed();
	
	// 建造房子后，将产品（房子）返回
	public House buildHouse(){
		return house;
	}
}

class CommonHouse extends HouseBuilder{
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
}

class HighBuilding extends HouseBuilder{
	@Override
	public void buildBasic() {
		System.out.println("给高楼打地基");
		
	}

	@Override
	public void buildWalls() {
		System.out.println("给高楼打砌墙");
		
	}

	@Override
	public void roofed() {
		System.out.println("给高楼打封顶");
	}
}

// 指挥者，指定制作流程，返回产品
class HouseDirector{
	
	HouseBuilder houseBuilder = null;
	
	// 通过构造器传入 houseBuilder
	public HouseDirector(HouseBuilder houseBuilder){
		this.houseBuilder = houseBuilder;
	}

	// 通过setter方法传入houseBuilder
	public void setHouseBuilder(HouseBuilder houseBuilder) {
		this.houseBuilder = houseBuilder;
	}
	
	//如何处理建造房子的流程，交给指挥者
	public House constructHouse(){
		houseBuilder.buildBasic();
		houseBuilder.buildWalls();
		houseBuilder.roofed();
		return houseBuilder.buildHouse();
	}
	
}