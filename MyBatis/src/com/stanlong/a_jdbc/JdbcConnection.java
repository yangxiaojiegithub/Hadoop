package com.stanlong.a_jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.stanlong.util.JdbcUtil;

public class JdbcConnection {

	private String url = "jdbc:mysql://localhost:3306/scott";
	private String username = "root";
	private String password = "root";

	/**
	 * 使用驱动管理器类连接数据库
	 */
	@Test
	public void demo01() throws Exception {
		//Driver driver = new com.mysql.jdbc.Driver();
		// 1、注册驱动程序
		// 源码中这代代码是放在静态代码块里的，而静态代码块在类加载的时候就会执行，所以这一段相当于注册了两次
		// DriverManager.registerDriver(driver);

		// 2、连接到具体的数据库
		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println(conn);
	}

	@Test
	public void demo02() throws Exception {
		// 通过得到字节码的方式加载静态代码块，从而注册驱动程序
		Class.forName("com.mysql.jdbc.Driver");

		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println(conn);
	}

	/**
	 * 执行DDL语句
	 * 
	 * @throws Exception
	 */
	@Test
	public void demo03() {
		Connection conn = null;
		Statement stat = null;
		// 1、注册驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// 2、获取连接对象
			conn = DriverManager.getConnection(url, username, password);

			// 3、创建 Statement 对象
			stat = conn.createStatement();

			// 4、准备sql
			String sql = "CREATE DATABASE jdbc_database DEFAULT CHARACTER SET utf8";

			// 5、发送sql语句，执行sql语句，等到返回结果
			int count = stat.executeUpdate(sql);

			// 6、输出
			System.out.println("影响了" + count + "行");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 7、关闭连接（顺序， 后打开的要先关闭）
			JdbcUtil.close(stat, conn);
		}
	}

	/**
	 * 执行DML语句_新增
	 */
	@Test
	public void demo04() {
		url = "jdbc:mysql://localhost:3306/jdbc_database";
		Connection conn = null;
		Statement stat = null;
		try {
			// 1、注册驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 2、获得连接对象
			conn = DriverManager.getConnection(url, username, password);

			// 3、创建Statement对象
			stat = conn.createStatement();

			// 4、准备sql
			String sql = "insert into t_stu(name, age) values('stanlong', 22)";

			// 5、执行sql
			int count = stat.executeUpdate(sql);

			System.out.println("执行结果：" + count);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(stat, conn);
		}
	}

	/**
	 * 执行DML语句_修改
	 */
	@Test
	public void demo05() {
		url = "jdbc:mysql://localhost:3306/jdbc_database";
		Connection conn = null;
		Statement stat = null;
		try {
			// 1、注册驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 2、获得连接对象
			conn = DriverManager.getConnection(url, username, password);

			// 3、创建Statement对象
			stat = conn.createStatement();

			// 4、准备sql
			String sql = "update t_stu set name='zhangsan' where id='4'";

			// 5、执行sql
			int count = stat.executeUpdate(sql);

			System.out.println("执行结果：" + count);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(stat, conn);
		}

	}

	/**
	 * 执行DML语句_使用工具类
	 */
	@Test
	public void test06() {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = JdbcUtil.getConnection();
			stat = conn.createStatement();
			String sql = "insert into t_stu(name, age) values('stanlong', 23)";
			int count = stat.executeUpdate(sql);
			System.out.println("执行结果： " + count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stat, conn);
		}
	}

	/**
	 * 执行DQL语句_查询
	 */
	@Test
	public void test07() {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = JdbcUtil.getConnection();
			stat = conn.createStatement();
			String sql = "select * from t_stu where id = '4'";
			ResultSet rs = stat.executeQuery(sql);
			/*
			 * if(rs.next()){ //根据索引取列值 int id = rs.getInt(1); String name =
			 * rs.getString(2); int age = rs.getInt(3); System.out.println(
			 * "id: " + id + " name: "+ name + " age: "+ age);
			 * 
			 * //根据列名称取值 int id_c = rs.getInt("id"); String name_c =
			 * rs.getString("name"); int age_c = rs.getInt("age");
			 * System.out.println("id: " + id_c + " name: "+ name_c + " age: "+
			 * age_c); }
			 */

			// 遍历结果
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				System.out.println("id: " + id + " name: " + name + " age: " + age);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stat, conn);
		}
	}

	/**
	 * Preparestatement  新增
	 */
	@Test
	public void demo07() {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			// 1、获取连接
			conn = JdbcUtil.getConnection();

			// 2、准备sql
			String sql = "INSERT INTO t_stu(name, age) values(?, ?)";

			// 3、执行预编译的sql语句
			stat = conn.prepareStatement(sql);

			// 4、设置参数
			stat.setString(1, "lisi");
			stat.setInt(2, 28);

			// 5、发送参数，执行sql
			int count = stat.executeUpdate();

			System.out.println("执行结果： " + count);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stat, conn);
		}
	}
		
		
	/**
	 * Preparestatement  删除
	 */
	@Test
	public void demo08() {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			// 1、获取连接
			conn = JdbcUtil.getConnection();

			// 2、准备sql
			String sql = "DELETE FROM t_stu WHERE id = ?";

			// 3、执行预编译的sql语句
			stat = conn.prepareStatement(sql);

			// 4、设置参数
			stat.setInt(1, 4);
			// 5、发送参数，执行sql
			int count = stat.executeUpdate();

			System.out.println("执行结果： " + count);

		} catch (Exception e) {

		} finally {
			JdbcUtil.close(stat, conn);
		}
	}
	
	/**
	 * Preparestatement 查询
	 */
	@Test
	public void demo09() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			// 1、获取连接
			conn = JdbcUtil.getConnection();

			// 2、准备sql
			String sql = "SELECT * FROM t_stu WHERE id = ?";

			// 3、执行预编译的sql语句
			stat = conn.prepareStatement(sql);

			// 4、设置参数
			stat.setInt(1, 5);
			// 5、发送参数，执行sql
			rs = stat.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				System.out.println("id: " + id + " name: " + name + " age: " + age);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stat, conn, rs);
		}
	}
	
	/**
	 * Statement 模拟用户登录, 存在sql注入的风险
	 */
	@Test
	public void demo10(){
		//String name = "zhangsan"; //不安全，存在sql注入的风险，如用户输入格式为 "zhangsan' OR 1=1 -- "
		String name = "zhangsan' OR 1=1 -- ";  
		int age = 220;
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_stu where name = '"+name+"' and age = '"+age+"' ";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			if(rs.next()){
				System.out.println("登录成功！");
			}else{
				System.out.println("登录失败！");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(stat, conn, rs);
		}
	}
	
	/**
	 * PreparedStatement 模拟用户登录，较安全，可避免sql注入的风险
	 */
	@Test
	public void demo11(){
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_stu where name = ? and age = ? ";
			stat = conn.prepareStatement(sql);
			stat.setString(1, "zhangsan' OR 1=1 -- ");
			stat.setInt(2, 22);
			rs = stat.executeQuery();
			if(rs.next()){
				System.out.println("登录成功！");
			}else{
				System.out.println("登录失败！");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(stat, conn, rs);
		}
	}
	
	/**
	 * 使用CallableStatement 调用存储过程
	 * CALL pro_findById(5);
	 */
	@Test
	public void demo12(){
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			String sql = "CALL pro_findById(?)";
			stat = conn.prepareCall(sql);
			stat.setInt(1, 5);
			rs =stat.executeQuery();   //所有调用存储过程的语句都使用 executeQuery() 方法执行
			while(rs.next()){
				String name = rs.getString("name");
				int age = rs.getInt("age");
				System.out.println("name: " + name + ", age: " + age);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(stat, conn, rs);
		}
	}
	
	/**
	 * 带有输出参数的存储过程
	 * CALL pro_findById2(1, @uname);
	 */
	@Test
	public void demo13(){
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			String sql = "CALL pro_findById2(?, ?)"; //第一个？是输入参数， 第二个？是输出参数
			stat = conn.prepareCall(sql);
			//设置输入参数
			stat.setInt(1, 5);
			//设置输出参数(注册输出参数)
			/**
			 * 参数一：参数位置
			 * 参数二：存储过程中的输出参数的jdbc类型 VARCHAR(20)
			 */
			stat.registerOutParameter(2, java.sql.Types.VARCHAR);
			stat.executeQuery(); //结果返回到输出参数中
			//得到输出参数的值
			String result = stat.getString(2); //stat.getXXX()方法，专门用于获取存储过程中的输出参数
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(stat, conn, rs);
		}
	}
}
