package com.stanlong.bridge;
/**
 * 桥接模式
 * @author 矢量
 *
 * 2019年12月1日
 */
public class Bridge01 {
	public static void main(String[] args) {
		// 获取折叠式手机（样式+品牌）
		Phone phone1 = new FloderPhone(new XiaoMi());
		phone1.open();
		phone1.close();
		phone1.call();
		
		Phone phone2 = new FloderPhone(new Vivo());
		phone2.open();
		phone2.close();
		phone2.call();
	}
}

interface Brand{
	void open();
	void close();
	void call();
}

class XiaoMi implements Brand{

	@Override
	public void open() {
		System.out.println("小米手机开机");
		
	}

	@Override
	public void close() {
		System.out.println("小米手机关机");
		
	}

	@Override
	public void call() {
		System.out.println("小米手机拨打电话");
	}
}

class Vivo implements Brand{
	@Override
	public void open() {
		System.out.println("Vivo开机");
		
	}

	@Override
	public void close() {
		System.out.println("Vivo关机");
		
	}

	@Override
	public void call() {
		System.out.println("Vivo拨打电话");
	}
}

// 充当桥接类
abstract class Phone{
	// 组合品牌
	private Brand brand;

	// 构造器
	public Phone(Brand brand) {
		super();
		this.brand = brand;
	}
	
	protected void open() {
		this.brand.open();
	}
	protected void close() {
		this.brand.close();
	}
	protected void call() {
		this.brand.call();
	}
}

// 折叠式手机
class FloderPhone extends Phone{

	// 构造器
	public FloderPhone(Brand brand) {
		super(brand);
	}
	
	public void open(){
		super.open();
		System.out.println("折叠样式的手机");
	}
	public void close(){
		super.close();
		System.out.println("折叠样式的手机");
	}
	public void call(){
		super.call();
		System.out.println("折叠样式的手机");
	}
	
}