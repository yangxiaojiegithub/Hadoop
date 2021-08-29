package com.stanlong.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import com.stanlong.pojo.Items;

/**
 * 实现HttpRequestHandler接口
 * @author 矢量
 *
 */
public class ItemsHandler2 implements HttpRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用Service查到数据库，查询商品列表，这里使用静态数据模拟
		List<Items> itemList = new ArrayList<>();
		Items items1 = new Items();
		items1.setName("小米手机");
		items1.setPrice(2000.0f);
		items1.setDetail("不好用");
		
		Items items2 = new Items();
		items2.setName("小米笔记本");
		items2.setPrice(5000.0f);
		items2.setDetail("也不好用");
		
		itemList.add(items1);
		itemList.add(items2);
		
		//设置模型数据
		request.setAttribute("itemList", itemList);
		request.setAttribute("ItemsHandler", "ItemsHandler2");
		//设置转发视图
		request.getRequestDispatcher("/WEB-INF/jsp/items/itemList.jsp").forward(request, response);
		
		//使用此方法可以通过修改 response, 设置响应的数据格式，比如响应json数据
	}

}
