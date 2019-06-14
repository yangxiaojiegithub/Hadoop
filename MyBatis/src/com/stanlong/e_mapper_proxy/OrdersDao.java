package com.stanlong.e_mapper_proxy;

import java.util.List;

import com.stanlong.pojo.Orders;
import com.stanlong.pojo.OrdersCustomer;

public interface OrdersDao {

	public List<OrdersCustomer> findOrdersUser() throws Exception;
	
	public List<Orders> findOrdersUserResultMap() throws Exception;
	
	public List<Orders> findOrderAndOrderDetailResultMap() throws Exception;
}
