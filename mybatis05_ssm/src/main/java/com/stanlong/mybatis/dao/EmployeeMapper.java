package com.stanlong.mybatis.dao;

import java.util.List;

import com.stanlong.mybatis.pojo.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
	
	public List<Employee> getEmps();
}
