package com.stanlong.d_dao;

import com.stanlong.pojo.User;

public interface UserDao {

	public User findUserById(int id) throws Exception;
	
	public void insertUser(User user) throws Exception;
	
	public void deleteUserById(int id) throws Exception;
}
