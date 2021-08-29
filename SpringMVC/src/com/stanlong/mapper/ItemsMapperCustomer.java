package com.stanlong.mapper;

import java.util.List;

import com.stanlong.pojo.ItemsCustomer;
import com.stanlong.pojo.ItemsQueryVo;

public interface ItemsMapperCustomer {

	public List<ItemsCustomer> queryItemList(ItemsQueryVo itemsQueryVo) throws Exception;
	
}
