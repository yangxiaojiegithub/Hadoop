package com.stanlong.ocp.improve;

/**
 * 开闭原则
 * 新增功能时对原代码改动最小
 * @author Administrator
 *
 */
public class Ocp2 {
	public static void main(String[] args) {
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawShape(new Rectangle());
		graphicEditor.drawShape(new Circle());
	}
}

//这是一个用于绘图的类
class GraphicEditor{
	public void drawShape(Shape s){
		s.draw();
	}
}


// 基类为抽象类
abstract class Shape{
	int my_type;
	public abstract void draw();
}

class Rectangle extends Shape{
	Rectangle(){
		super.my_type = 1;
	}

	@Override
	public void draw() {
		System.out.println("绘制矩形");
		
	}
}

class Circle extends Shape{
	Circle(){
		super.my_type = 2;
	}
	@Override
	public void draw() {
		System.out.println("绘制圆形");
		
	}
}