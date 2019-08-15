package com.stanlong.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stanlong.mybatis.pojo.Employee;
import com.stanlong.mybatis.service.EmployeeService;

@Controller
public class EmployeeController {
	
	EmployeeService employeeService;

	@RequestMapping("/getemps")
	public String emps(Map<String, Object> map){
		List<Employee> emps = employeeService.getEmps();
		map.put("allEmps", emps);
		return "list";
	}
}
