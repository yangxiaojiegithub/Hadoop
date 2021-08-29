package com.stanlong.inversion.improve;

/**
 * 符合依赖倒转原则，依赖接口
 * @author Administrator
 *
 */
public class DependecyInversion2 {

	public static void main(String[] args) {
		Person person = new Person();
		person.receive(new Email());
		person.receive(new WeChat());
	}
}

// 定义接口
interface IRecever{
	String getInfo();
}


class Email implements IRecever{
	public String getInfo(){
		return "电子邮件信息： hello,world";
	}
}

class WeChat implements IRecever{
	public String getInfo(){
		return "微信信息： hello,world";
	}
}

class Person{
	// 这里是对接口的依赖
	public void receive(IRecever receiver){
		System.out.println(receiver.getInfo());
	}
}
