package com.stanlong.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.stanlong.pojo.Items;

/**
 * 实现Controller接口
 * @author 矢量
 *
 */
public class ItemsHandler implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		
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
		
		//返回ModeAndView
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", itemList); //相当于request的Attribut, 在jsp页面中通过itemList取数据
		modelAndView.addObject("ItemsHandler", "ItemsHandler");
		modelAndView.setViewName("items/itemList"); //指定视图
		
		return modelAndView;
	}

}
