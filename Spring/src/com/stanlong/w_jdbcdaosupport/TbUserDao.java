package com.stanlong.w_jdbcdaosupport;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class TbUserDao extends JdbcDaoSupport{

	//更改用户
	public void updateUser(Tb_User tb_user){
		String sql = "update tb_user set username = ?, password = ? where id=?";
		Object args[] = {tb_user.getUsername(), tb_user.getPassword(), tb_user.getId()};
		this.getJdbcTemplate().update(sql, args);
	}
	
	//查询所有用户
	public List<Tb_User> findAll(){
		String sql = "select * from tb_user";
		RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<Tb_User>(Tb_User.class);
		List<Tb_User> userList = (List<Tb_User>)this.getJdbcTemplate().query(sql, rowMapper);
		return userList;
	}
}
