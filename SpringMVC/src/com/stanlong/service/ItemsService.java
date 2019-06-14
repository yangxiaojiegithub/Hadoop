package com.stanlong.service;

import java.util.List;

import com.stanlong.pojo.ItemsCustomer;
import com.stanlong.pojo.ItemsQueryVo;

public interface ItemsService {

	public List<ItemsCustomer> queryItemList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	public ItemsCustomer findItemById(Integer id) throws Exception;
	
	public void updateItemById(Integer id, ItemsCustomer itemsCustomer) throws Exception;
	
}
