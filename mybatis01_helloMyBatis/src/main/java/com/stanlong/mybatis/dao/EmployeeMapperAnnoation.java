package com.stanlong.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import com.stanlong.mybatis.pojo.Employee;

public interface EmployeeMapperAnnoation {

	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmpById(Integer id);
}
