package com.stanlong.inversion;

/**
 * 不符合依赖倒转原则，依赖具体的类 
 * @author Administrator
 *
 */
public class DependecyInversion {

	public static void main(String[] args) {
		Person person = new Person();
		person.receive(new Email());
	}
}

class Email{
	public String getInfo() {
		return "电子邮件信息： hello,world";
	}
}

// 完成 Person 接收消息的功能
// 方式一完成
// 1、简单，比较容易想到
// 2、如果我们获取的对象是微信或者短信等等，则需要新增类。同时Person也要增加相应的方法
// 3、解决思路：引入一个抽象的IReceiver， 表示接收者，这样 Person 类 与IRecever发生依赖
//    因为Email， wechat 等等属于接收的范围，他们各自实现IRecever接口就OK，这样就符合依赖倒转原则
class Person{
	public void receive(Email email){
		System.out.println(email.getInfo());
	}
}


