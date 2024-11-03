package org.simple4j.apiapovalidator.test.testapi.japi;

public class Product
{
	private int productId = -1;
	private String productName = null;
	public int getProductId()
	{
		return productId;
	}
	public void setProductId(int productId)
	{
		this.productId = productId;
	}
	public String getProductName()
	{
		return productName;
	}
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [productId=").append(productId).append(", productName=").append(productName)
				.append("]");
		return builder.toString();
	}
}
