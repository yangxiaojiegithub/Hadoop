package com.stanlong.demeter;

import java.util.ArrayList;
import java.util.List;

/**
 * 迪米特法则
 * @author Administrator
 *
 */
public class Demeter1 {
	public static void main(String[] args) {
		SchoolManager schoolManager = new SchoolManager();
		// 输出学院员工id和学校总部员工id
		schoolManager.printAllEmployee(new CollegeManager());
		
	}
}

// 学校总部员工
class Employee{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

// 学院的员工
class CollegeEmployee{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

// 管理学院员工
class CollegeManager{
	public List<CollegeEmployee> getAllEmployee(){
		List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
		for(int i=0; i<10; i++){
			CollegeEmployee emp = new CollegeEmployee();
			emp.setId("学院的员工id=" + i);
			list.add(emp);
		}
		return list;
	}
}

// 学校的管理类
// 分析 SchoolManager 的直接朋友类，   Employee， CollegeManager。
// 而 CollegeEmployee不是直接朋友，因为它是以局部变量的形式出现的类，违背了迪米特法则
// 直接朋友：出现成员变量，方法参数，方法返回值中的类为直接朋友
class SchoolManager{
	public List<Employee> getAllEmployee(){
		List<Employee> list = new ArrayList<Employee>();
		for(int i=0; i<5; i++){
			Employee emp = new Employee();
			emp.setId("学院的员工id=" + i);
			list.add(emp);
		}
		return list;
	}
	
	// 输出学校总部和学院员工信息
	void printAllEmployee(CollegeManager sub){
		// 
		List<CollegeEmployee> list1 = sub.getAllEmployee();
		System.out.println("---------------------分公司员工-------------------------");
		for (CollegeEmployee e : list1) {
			System.out.println(e.getId());
		}
		
		List<Employee> list2 = this.getAllEmployee();
		System.out.println("---------------------学校总部员工-------------------------");
		for (Employee e : list2) {
			System.out.println(e.getId());
		}
	}
}