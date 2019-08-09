package com.stanlong.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.stanlong.mybatis.dao.EmployeeMapper;
import com.stanlong.mybatis.pojo.Employee;

/**
 * 测试增删改
 * mybatis允许增删改直接定义以下类型的返回值
 * 	Integer Long Boolean
 * 我们需要手动提交数据
 * 	sessionFactory.openSession();  --》手动提交
 * 	sessionFactory.openSession(true); --》自动提交
 * @author 矢量
 *
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis/SqlSessionConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 测试命名参数
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//测试命名参数
			//Employee employee = mapper.getEmpByIdAndLastName(4, "lisi");
			
			//将参数封装到map里
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", 4);
			map.put("lastName", "lisi");
			Employee employee = mapper.getEmpMap(map);
			System.out.println(employee);
		}finally{
			openSession.close();
		}
	}
	
	/**
	 * 测试新增
	 * @throws Exception
	 */
	@Test
	public void testAddEmp() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//测试新增
			Employee employee = new Employee(null, "lisi", "lisi@sina.cn", "m");
			mapper.addEmp(employee);
			System.out.println(employee.getId()); //自增主键值
			
			//测试修改
			/*Employee employee = new Employee(2, "lisi_wangwu", "lisi@sina.cn", "m");
			mapper.updateEmp(employee);*/
			
			//测试删除
			//mapper.deleteById(2);
			openSession.commit();
		}finally{
			openSession.close();
		}
	}

}
