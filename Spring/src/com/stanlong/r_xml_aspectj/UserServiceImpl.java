package com.stanlong.r_xml_aspectj;

public class UserServiceImpl implements UserService {

	@Override
	public String addUser() {
		System.out.println("r_xml_aspectj addUser");
		return "StanLong";
	}

	@Override
	public void updateUser() {
		int i = 1/0;
		System.out.println("r_xml_aspectj updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("r_xml_aspectj deleteUser");
	}

}
