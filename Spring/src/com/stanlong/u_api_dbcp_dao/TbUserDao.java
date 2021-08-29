package com.stanlong.u_api_dbcp_dao;

import org.springframework.jdbc.core.JdbcTemplate;

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
}
