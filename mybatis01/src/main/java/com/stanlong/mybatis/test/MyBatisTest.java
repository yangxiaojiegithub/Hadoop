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
 * 1、接口式编程
 * 2、SqlSession 代表和数据库的一次会话，用完必须关闭
 * 3、SqlSession 和 connection 一样，都是非线程安全的，每次使用都应该获取新的对象
 * 4、mapper接口没有实现类， 但是mybatis会为这个接口生成一个代理对象，将接口和xml进行绑定
 * 5、两个重要的配置文件
 * 	mybatis的全局配置文件，包含数据库连接信息，事务管理器信息，系统运行环境信息
 * 	sql映射文件，保存了每一个sql语句的映射信息
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
	 * 1、根据 SqlSessionConfig.xml(全局配置文件) 创建一个 sqlSessionFactory 对象， sqlSessionFactory
	 * 2、sql映射文件。配置了每一个sql,以及sql的封闭规则 等
	 * 3、将sql映射文件注册在全局配置文件中
	 * 4、写代码
	 * 		1)根据全局配置文件得到 SqlSessionFactory
	 * 		2)使用sqlSession工厂，获取到selSession对象使用他来执行增删改查
	 * 		一个sqlSession就是代表和数据库的一次会话，用完关闭
	 * 		3)使用sql的唯一标识 告诉mybatis执行哪个sql,sql都是保存在Sql映射文件中
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException{
		String resource = "mybatis/SqlSessionConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//获取 openSession 实例，能直接执行已映射的sql语句
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try{
			//arg0 sql的唯一标识
			//arg1 执行sql要用的参数
			Employee employee = openSession.selectOne("com.stanlong.mybatis.EmployeeMapper.selectEmp", 1);
			System.out.println(employee);
		}finally{
			openSession.close();
		}
	}
	
	@Test
	public void test01() throws IOException{
		//获取sqlSessionFactory对象
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		
		//获取 sqlSession对象
		SqlSession openSession = sessionFactory.openSession();
		
		//获取接口的实现类对象
		try{
			//mybatis会为接口创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			System.out.println(mapper.getClass());  //输出结果  class com.sun.proxy.$Proxy4
			System.out.println(employee);
		}finally{
			openSession.close();
		}
		
	}
	
	
}
