package com.stanlong.pojo;

import java.util.List;
import java.util.Map;

/**
 * 商品的包装对象
 * @author 矢量
 *
 */
public class ItemsQueryVo {

	private Items items;
	
	private ItemsCustomer itemsCustomer;
	
	//List参数绑定
	private List<ItemsCustomer> itemsList;
	
	//Map参数绑定
	private Map<String, Object> itemsInfo;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public ItemsCustomer getItemsCustomer() {
		return itemsCustomer;
	}

	public void setItemsCustomer(ItemsCustomer itemsCustomer) {
		this.itemsCustomer = itemsCustomer;
	}

	public List<ItemsCustomer> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsCustomer> itemsList) {
		this.itemsList = itemsList;
	}

	public Map<String, Object> getItemsInfo() {
		return itemsInfo;
	}

	public void setItemsInfo(Map<String, Object> itemsInfo) {
		this.itemsInfo = itemsInfo;
	}
	
	
	
}
