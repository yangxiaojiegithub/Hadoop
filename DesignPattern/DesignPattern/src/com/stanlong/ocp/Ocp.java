package com.stanlong.ocp;

/**
 * 开闭原则
 * 分析：
 * 违犯了设计模式的ocp原则，即对扩展开放，对修改关闭。即当我们给类增加新的功能时，尽量不修改代码。或者尽量少修改代码
 * @author Administrator
 *
 */
public class Ocp {

	public static void main(String[] args) {
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawRectangle(new Rectangle());
		graphicEditor.drawCircle(new Circle());
	}
	
}

// 这是一个用于绘图的类
class GraphicEditor{
	// 接收Shape对象，根据不同的type，来绘制不同的图形
	public void drawShape(Shape s){
		if(s.my_type == 1){
			drawRectangle(s);
		}else if(s.my_type==2){
			drawCircle(s);
		}
	}
	
	public void drawRectangle(Shape r){
		System.out.println("矩形");
	}
	
	public void drawCircle(Shape c){
		System.out.println("圆形");
	}
}

class Shape{
	int my_type;
}

class Rectangle extends Shape{
	Rectangle(){
		super.my_type = 1;
	}
}

class Circle extends Shape{
	Circle(){
		super.my_type = 2;
	}
}