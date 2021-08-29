package com.stanlong.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.stanlong.mapper.ItemsMapper;
import com.stanlong.mapper.ItemsMapperCustomer;
import com.stanlong.pojo.Items;
import com.stanlong.pojo.ItemsCustomer;
import com.stanlong.pojo.ItemsQueryVo;
import com.stanlong.service.ItemsService;

public class ItemsServiceImpl implements ItemsService{
	
	@Autowired
	private ItemsMapperCustomer itemsMapperCustomer;
	
	@Autowired
	private ItemsMapper ItemsMapper;

	@Override
	public List<ItemsCustomer> queryItemList(ItemsQueryVo itemsQueryVo) throws Exception {
		
		return itemsMapperCustomer.queryItemList(itemsQueryVo);
	}

	@Override
	public ItemsCustomer findItemById(Integer id) throws Exception {
		Items items = ItemsMapper.selectByPrimaryKey(id);
		ItemsCustomer itemsCustomer = null;
		//将 items 的属性值 拷贝到 itemsCustomer
		if(null != items){
			itemsCustomer = new ItemsCustomer();
			BeanUtils.copyProperties(items, itemsCustomer);
		}
		return itemsCustomer;
	}

	@Override
	public void updateItemById(Integer id, ItemsCustomer itemsCustomer) throws Exception {
		itemsCustomer.setId(id);
		ItemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustomer);
		
	}

}
