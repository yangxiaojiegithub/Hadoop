package com.stanlong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stanlong.pojo.ItemsCustomer;
import com.stanlong.service.ItemsService;

@Controller
public class JsonTest {
	
	@Autowired
	private ItemsService itemsService;
	
	@RequestMapping("/jsonTest")
	public String testJson(){
		return "jsonTest";
	}
	

	@RequestMapping("/requestJson")
	//@RequestBody将请求的商品信息的json串转成itemsCustom对象
	//@ResponseBody将itemsCustom转成json输出
	public @ResponseBody ItemsCustomer requestJson(@RequestBody ItemsCustomer itemsCustomer){
		return itemsCustomer;
	}
	
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustomer responseJson(ItemsCustomer itemsCustomer){
		return itemsCustomer;
	}
	
	/**
	 * 查询商品信息，输出json
	 * RestFul风格的url
	 * "/restfulItem/{id}" 里面的id表示将这个位置的参数传到 @PathVariable 指定的名称中
	 */
	@RequestMapping("/restfulItem/{id}")
	public @ResponseBody ItemsCustomer restfulItem(@PathVariable("id") Integer id) throws Exception{
		ItemsCustomer itemsCustomer = itemsService.findItemById(id);
		return itemsCustomer;
	}
}
