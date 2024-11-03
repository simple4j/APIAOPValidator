package org.simple4j.apiapovalidator.test.testapi.japi;

import java.util.List;
import java.util.Map;

import org.simple4j.apiaopvalidator.beans.AppResponse;

public interface OrderAPI
{

	public AppResponse<String> placeOrder(Order order);
	public AppResponse<Order> getOrder(String orderId);
	
	public AppResponse<List<String>> placeBulkOrders(Order[] orders);
	public AppResponse<Void> placeBulkOrders(Map<String,Order> orders);
}
