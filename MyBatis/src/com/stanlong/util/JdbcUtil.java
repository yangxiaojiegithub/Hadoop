package com.stanlong.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * jdbc工具类
 * 
 * @author 矢量
 *
 */
public class JdbcUtil {

	private static String url = null;
	private static String driverClass = null;
	private static String username = null;
	private static String password = null;

	/**
	 * 静态代码块（只加载一次）
	 */
	static {
		try {
			Properties props = new Properties();
			// 读取db.properties文件
			/**
			 * . 代表java 运行目录
			 * 在java项目下，  . 代表 java命令运行项目的根目录
			 * 在web项目下， java 命令的运行目录从 tomcat/bin目录开始
			 * 所以在web项目中不能使用.表示项目路径， 应该使用类路径的读取方式
			 * 
			 */
			//FileInputStream fin = new FileInputStream("./src/com/stanlong/b_jdbc_servlet/db.properties");
			
			/**
			 * 使用类路径的读取方式
			 * / ：斜杠表示 classpath的根目录
			 * 在java项目下 classpath 的根目录从 bin开始
			 * 在web项目下， classpath 的根目录从 WEB-INF/classes 目录
			 */
			InputStream in = JdbcUtil.class.getResourceAsStream("/properties/jdbc_db.properties");
			// 加载文件
			props.load(in);
			// 读取信息
			url = props.getProperty("url");
			driverClass = props.getProperty("driverClass");
			username = props.getProperty("username");
			password = props.getProperty("password");

			// 注册驱动程序
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("驱动程序注册出错");
		}
	}

	/**
	 * 获取连接对象的方法
	 */
	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 释放资源
	 */
	public static void close(Statement stat, Connection conn) {
		if (null != stat) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	/**
	 * 方法重载
	 * 
	 * @param stat
	 * @param conn
	 */
	public static void close(Statement stat, Connection conn, ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != stat) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
}
