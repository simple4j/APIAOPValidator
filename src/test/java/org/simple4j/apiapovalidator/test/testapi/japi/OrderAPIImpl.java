package org.simple4j.apiapovalidator.test.testapi.japi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.simple4j.apiaopvalidator.beans.AppResponse;

public class OrderAPIImpl implements OrderAPI
{

	@Override
	public AppResponse<String> placeOrder(Order order)
	{
		AppResponse<String> ret = new AppResponse<String>();
		ret.responseObject = "111";
		return ret ;
	}

	@Override
	public AppResponse<Order> getOrder(String orderId)
	{
		AppResponse<Order> ret = new AppResponse<Order>();
		Order order = new Order();
		order.setOrderId(orderId);
		ret.responseObject = order ;
		return ret ;
	}

	@Override
	public AppResponse<List<String>> placeBulkOrders(Order[] orders)
	{
		AppResponse<List<String>> ret = new AppResponse<List<String>>();
		List<String> orderIds = new ArrayList<String>();
		for(int i = 0 ; i < orders.length ; i++)
		{
			orderIds.add(""+i);
		}
		ret.responseObject = orderIds ;
		return ret ;
	}

	@Override
	public AppResponse<Void> placeBulkOrders(Map<String, Order> orders)
	{
		AppResponse<Void> ret = new AppResponse<Void>();
		Void v = null;
		ret.responseObject = v ;
		return ret ;
	}


}
