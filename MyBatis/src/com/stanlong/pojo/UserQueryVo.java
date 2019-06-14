package com.stanlong.pojo;

import java.util.List;

public class UserQueryVo {

	//传入多个id
	private List<Integer> ids;
	
	//用户查询条件
	private UserCustomer userCustomer;

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	
	
	
	
}
