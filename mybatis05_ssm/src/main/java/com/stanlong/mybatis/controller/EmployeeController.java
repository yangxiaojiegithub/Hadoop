package com.stanlong.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stanlong.mybatis.pojo.Employee;
import com.stanlong.mybatis.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	@RequestMapping("/getEmps")
	public String getEmps(Map<String, Object> map){
		List<Employee> emps = employeeService.getEmps();
		map.put("allEmps", emps);
		return "list";
	}
}
