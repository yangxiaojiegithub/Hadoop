package com.stanlong.n_aop_jdk;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("n_aop_jdk addUser");
	}

	@Override
	public void updateUser() {
		System.out.println("n_aop_jdk updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("n_aop_jdk deleteUser");
	}

}
