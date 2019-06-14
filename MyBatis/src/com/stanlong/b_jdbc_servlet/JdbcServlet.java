package com.stanlong.b_jdbc_servlet;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stanlong.util.JdbcUtil;

public class JdbcServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServerException, IOException{
		//获得连接
		Connection conn = JdbcUtil.getConnection();
		System.out.println(conn);
	}
}
