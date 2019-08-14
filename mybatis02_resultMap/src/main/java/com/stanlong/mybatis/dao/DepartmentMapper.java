package com.stanlong.mybatis.dao;

import com.stanlong.mybatis.pojo.Department;

public interface DepartmentMapper {

	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);
	
	public Department getDeptByIdStep(Integer id);
}
