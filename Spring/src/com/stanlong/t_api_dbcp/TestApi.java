package com.stanlong.t_api_dbcp;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestApi {

	public static void main(String[] args) {
		//创建连接池
		BasicDataSource basicDataSource = new BasicDataSource();
		
		//基本四项
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/spring_jdbcTemplent");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("root");
		
		//创建模板
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(basicDataSource);
		
		//通过API操作
		jdbcTemplate.update("insert into tb_user(username, password) values (?,?);", "wangwu", "789");
	}
}
