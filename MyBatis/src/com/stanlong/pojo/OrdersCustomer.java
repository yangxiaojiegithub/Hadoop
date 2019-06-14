package com.stanlong.pojo;

/**
 * 订单扩展类
 * @author 矢量
 *
 */
public class OrdersCustomer extends Orders{

	private String username;
	private String sex;
	private String address;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "OrdersCustomer [username=" + username + ", sex=" + sex + ", address=" + address + "]";
	}
	
	
	
}
