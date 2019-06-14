package com.stanlong.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.stanlong.pojo.Items;

/**
 * 注解Handler
 * @author 矢量
 *
 */
@Controller
public class ItemsHandler3{

	//@RequestMapping 将方法 queryItems 和 url /queryItems 进行映射，一个方法映射一个url
	//一般建议将url和方法写成一致，方便维护
	@RequestMapping("/queryItems4")
	public ModelAndView queryItems(){
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
		modelAndView.addObject("ItemsHandler", "ItemsHandler3");
		modelAndView.setViewName("items/itemList"); //指定视图
		
		return modelAndView;
	}
}
