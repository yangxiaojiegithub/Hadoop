package com.stanlong.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.stanlong.mybatis.dao.EmployeeMapperDynamicSQL;
import com.stanlong.mybatis.pojo.Department;
import com.stanlong.mybatis.pojo.Employee;

/**
 * 测试动态sql
 * @author 矢量
 *
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis/SqlSessionConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void test01() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			//测试if
			/*EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(8,"%li%", null, null);
			List<Employee> empList = mapper.getEmpsByConditionIf(employee);
			for (Employee emp : empList) {
				System.out.println(emp);
			}*/
			
			//测试trim
			/*EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null,"%li%", null, null);
			List<Employee> empList = mapper.getEmpsByConditionTrim(employee);
			for (Employee emp : empList) {
				System.out.println(emp);
			}*/
			
			//测试choose
			/*EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null, null, null, null);
			List<Employee> empList = mapper.getEmpsByConditionChoose(employee);
			for (Employee emp : empList) {
				System.out.println(emp);
			}*/
			
			//测试set标签
			/*EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(8, "wangwu", null, null);
			mapper.updateEmp(employee);
			openSession.commit();*/
			
			//测试foreach标签
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Employee> empList = mapper.getEmpsByConditionForeach(Arrays.asList(1,2,4));
			for (Employee emp : empList) {
				System.out.println(emp);
			}
		}finally{
			openSession.close();
		}
	}
	
	@Test
	public void test02() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Employee> emps = new ArrayList<Employee>();
			emps.add(new Employee(null, "zhaoliu", "zhaoliu@123.cn,","m", new Department(1)));
			emps.add(new Employee(null, "liqi", "liqi@123.cn,","m", new Department(1)));
			emps.add(new Employee(null, "zhuba", "zhuba@123.cn,","f", new Department(2)));
			emps.add(new Employee(null, "chenjiu", "chenjiu@123.cn,","f", new Department(2)));
			mapper.addEmps(emps);
			openSession.commit();
		}finally{
			openSession.close();
		}
	}
	
	@Test
	public void test03() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee2 = new Employee();
			employee2.setLastName("lisi");
			List<Employee> empList = mapper.getEmpsTestInnerParameter(employee2);
			for (Employee employee : empList) {
				System.out.println(employee);
			}
		}finally{
			openSession.close();
		}
	}
}
