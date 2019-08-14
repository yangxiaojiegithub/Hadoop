package com.stanlong.mybatis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.stanlong.mybatis.pojo.Employee;

public interface EmployeeMapper {

	public Employee getEmpMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

	public void addEmp(Employee employee);

	public void updateEmp(Employee employee);

	public void deleteById(Integer id);
	
	//返回一条记录的map， key就是列名，值就是对应 的值
	public Map<String, Object> getEmpByIdReturnMap(Integer id);
	
	//返回多条记录的map， key是对应的主键，值就是封装后的java对象
	//@MapKey 告诉mybatis封装map的时候使用哪个属性作为主键
	@MapKey("id")
	public Map<String, Object> getEmpByLastNameLikeReturnMap(String lastName);
}
