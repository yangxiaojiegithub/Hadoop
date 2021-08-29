package com.stanlong.jsonpclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ServerCloneException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jsonpServer")
public class jsonpServer extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String funname = req.getParameter("callback");
		resp.setContentType("application/x-javascript");
		PrintWriter writer = resp.getWriter();
		writer.print(funname + "('data123');");
		writer.flush();
		writer.close();
	}
}