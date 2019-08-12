package com.stanlong.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.stanlong.mybatis.dao.EmployeeMapper;
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
	
	/**
	 * 两级缓存
	 * 一级缓存（本地缓存）：sqlSession级别的缓存，一级缓存是一起开启的: SqlSession级别的一个map
	 * 	与数据库同一次会话期间查询到的数据会放在本地缓存中。
	 * 	如果以后需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
	 * 	一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是还需要再向数据库发出查询）
	 * 1、sqlSession不同
	 * 2、sqlSession相同，查询条件不同（当前一级缓存中还没有这个数据）
	 * 3、sqlSession相同，但是在两次查询之间有增删改操作（增删改可能对当前数据有影响）
	 * 4、sqlSession相同，手动清除了一级缓存  openSession.clearCache();
	 * 二级缓存（全局缓存）:基于 namespace级别的缓存，一个namespace对应一个二级缓存
	 * 	工作机制：
	 * 	1、一个会话，查询一条数据这个数据就会被放在一级缓存中
	 * 	2、如果会话关闭，那么一级缓存中的数据会被在保存到二级缓存中，新的会话查询信息就会参照二级缓存
	 * 
	 * 使用二级缓存：
	 * 		效果：数据会从二级缓存中获取，查出的数据默认都会被先放在一级缓存中，只有会话提交或者关闭后，一级缓存中的数据才会㔹到二级缓存中
	 * 	1、开启全局二级缓存的配置 <setting name="cacheEnabled" value="true"/>
	 * 	2、去mapper.xml中配置使用二级缓存 <cache></cache>
	 * 	3、pojo需要实现序列化接口
	 */
	
	/**
	 * 测试一级缓存
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp01 = mapper.getEmpById(8);
			System.out.println(emp01);
			Employee emp02 = mapper.getEmpById(8);
			System.out.println(emp02);
			System.out.println(emp01==emp02);  //输出结果为true
		}finally{
			openSession.close();
		}
	}
	
	/**
	 * 测试二级缓存
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		SqlSession openSession2 = sessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			Employee employee01 = mapper.getEmpById(1);
			System.out.println(employee01);
			openSession.close();
			
			Employee employee02 = mapper2.getEmpById(1);
			System.out.println(employee02);
			openSession2.close();
			
		}finally{
		}
		
	}
}
