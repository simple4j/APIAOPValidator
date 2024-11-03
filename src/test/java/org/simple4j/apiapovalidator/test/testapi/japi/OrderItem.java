package org.simple4j.apiapovalidator.test.testapi.japi;

public class OrderItem
{
	private String orderItemId = null;
	private Product product = null;
	private int quantity = -1;
	public String getOrderItemId()
	{
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId)
	{
		this.orderItemId = orderItemId;
	}
	public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [orderItemId=").append(orderItemId).append(", product=").append(product)
				.append(", quantity=").append(quantity).append("]");
		return builder.toString();
	}
	
}
