package com.stanlong.inversion.method;

/**
 * 符合依赖倒转原则，依赖关系的传递方式
 * @author Administrator
 *
 */
public class DependecyInversion3 {

	public static void main(String[] args) {
		// 方式一调用
		/*ChangeHong ch = new ChangeHong();
		OpenAndClose openAndClose = new OpenAndClose();
		openAndClose.open(ch);*/
		
		// 方式二调用
		/*ChangeHong ch = new ChangeHong();
		OpenAndClose openAndClose = new OpenAndClose(ch);
		openAndClose.open();*/
		
		// 方式三调用
		ChangeHong ch = new ChangeHong();
		OpenAndClose openAndClose = new OpenAndClose();
		openAndClose.setTV(ch);
		openAndClose.open();
	}
}

// 方式1， 通过接口传递
/*interface IOpenAndClose{
	public void open(ITV tv);
}

interface ITV{
	public void play();
}

class OpenAndClose implements IOpenAndClose{
	public void open(ITV tv){
		tv.play();
	}
}*/

// 方式2， 通过构造方法传递依赖
/*interface IOpenAndClose{
	public void open();
}

interface ITV{
	public void play();
}

class OpenAndClose implements IOpenAndClose{
	public ITV tv;
	public OpenAndClose(ITV tv){
		this.tv = tv;
	}
	@Override
	public void open() {
		this.tv.play();
	}
}*/

// 方式3， 通过setter方法传递
interface IOpenAndClose{
	public void open();
	public void setTV(ITV tv);
}

interface ITV{
	public void play();
}

class OpenAndClose implements IOpenAndClose{
	public ITV tv;

	@Override
	public void setTV(ITV tv) {
		this.tv = tv;
	}
	
	@Override
	public void open() {
		this.tv.play();
	}
}

class ChangeHong implements ITV{

	@Override
	public void play() {
		System.out.println("长虹电视机打开");
	}
	
}

