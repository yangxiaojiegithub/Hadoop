package com.stanlong.i_spel;

public class Customer {

	private String cname ="lisi";
	private Double pi;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Double getPi() {
		return pi;
	}
	public void setPi(Double pi) {
		this.pi = pi;
	}
	@Override
	public String toString() {
		return "Customer [cname=" + cname + ", pi=" + pi + "]";
	}
	
	
}
