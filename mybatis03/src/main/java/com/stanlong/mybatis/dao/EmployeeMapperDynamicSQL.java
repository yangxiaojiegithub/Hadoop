package com.stanlong.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stanlong.mybatis.pojo.Employee;

public interface EmployeeMapperDynamicSQL {

	/**
	 * 根据任意字段查询员工
	 * @param employee
	 * @return
	 */
	public List<Employee> getEmpsByConditionIf(Employee employee);
	
	public List<Employee> getEmpsByConditionTrim(Employee employee);
	
	public List<Employee> getEmpsByConditionChoose(Employee employee);
	
	public void updateEmp(Employee employee);
	
	public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);
	
	public void addEmps(@Param("emps")List<Employee> emps);
	
	public List<Employee> getEmpsTestInnerParameter(Employee employee);
}
