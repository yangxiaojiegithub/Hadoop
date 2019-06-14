package com.stanlong.v_c3p0;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class TbUserDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void updateUser(Tb_User tb_user){
		String sql = "update tb_user set username = ?, password = ? where id=?";
		Object args[] = {tb_user.getUsername(), tb_user.getPassword(), tb_user.getId()};
		jdbcTemplate.update(sql, args);
	}
	
	public List<Tb_User> findAll(){
		String sql = "select * from tb_user";
		RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<Tb_User>(Tb_User.class);
		List<Tb_User> userList = (List<Tb_User>)jdbcTemplate.query(sql, rowMapper);
		return userList;
	}
}
