package org.simple4j.apiapovalidator.test.testapi.japi;

import java.util.List;

public class Order
{

	private String orderId = null;
	private List<OrderItem> orderItems = null;
	public String getOrderId()
	{
		return orderId;
	}
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}
	public List<OrderItem> getOrderItems()
	{
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems)
	{
		this.orderItems = orderItems;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [orderId=").append(orderId).append(", orderItems=").append(orderItems)
				.append("]");
		return builder.toString();
	}

}
