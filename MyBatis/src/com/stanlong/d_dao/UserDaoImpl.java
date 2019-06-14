package com.stanlong.d_dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.stanlong.pojo.User;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	//sqlSession 
	@Override
	public User findUserById(int id) throws Exception {
		SqlSession sqlSession = this.getSqlSession();
		User user = sqlSession.selectOne("User.findUserById", id);
		return user;
	}

	@Override
	public void insertUser(User user) throws Exception {
		SqlSession sqlSession = this.getSqlSession();
		sqlSession.insert("User.insertUser", user);
	}

	@Override
	public void deleteUserById(int id) throws Exception {
		SqlSession sqlSession = this.getSqlSession();
		sqlSession.delete("User.deleteUserById", id);
	}

}
