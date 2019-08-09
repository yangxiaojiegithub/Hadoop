package com.stanlong.mybatis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stanlong.mybatis.pojo.Employee;

public interface EmployeeMapper {

	public Employee getEmpMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

	public void addEmp(Employee employee);

	public void updateEmp(Employee employee);

	public void deleteById(Integer id);
}
