package org.simple4j.apiapovalidator.test;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simple4j.apiaopvalidator.beans.AppResponse;
import org.simple4j.apiapovalidator.test.testapi.japi.Order;
import org.simple4j.apiapovalidator.test.testapi.japi.OrderAPI;
import org.simple4j.apiapovalidator.test.testapi.japi.OrderItem;
import org.simple4j.apiapovalidator.test.testapi.japi.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest
{

	/**
	 * Sample API used to test the library. The API does not implement any real business logic
	 */
    OrderAPI api = null;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");

        api = context.getBean("orderService", OrderAPI.class);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testPlaceOrderNullAgument()
	{
		AppResponse<String> placeOrderResponse = api.placeOrder(null);
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("[0]-missing");
		Assert.assertTrue("testPlaceOrderNullAgument", testResult);
	}

	@Test
	public void testPlaceOrderNullOrderItems()
	{
		Order order = new Order();
		order.setOrderItems(null);
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems-missing");
		Assert.assertTrue("testPlaceOrderNullOrderItems", testResult);
	}

	@Test
	public void testPlaceOrderMinOrderItems()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems-minsize");
		Assert.assertTrue("testPlaceOrderMinOrderItems", testResult);
	}
	@Test
	public void testPlaceOrderMaxOrderItems()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		orderItems.add(oi );
		oi = new OrderItem();
		orderItems.add(oi );
		oi = new OrderItem();
		orderItems.add(oi );
		oi = new OrderItem();
		orderItems.add(oi );
		oi = new OrderItem();
		orderItems.add(oi );
		oi = new OrderItem();
		orderItems.add(oi );
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems-maxsize");
		Assert.assertTrue("testPlaceOrderMaxOrderItems", testResult);
	}
	@Test
	public void testPlaceOrderMinvalueQuantity()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(-1);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].quantity-minvalue");
		Assert.assertTrue("testPlaceOrderMinvalueQuantity", testResult);
	}

	@Test
	public void testPlaceOrderMaxvalueQuantity()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(6);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].quantity-maxvalue");
		Assert.assertTrue("testPlaceOrderMaxvalueQuantity", testResult);
	}

	@Test
	public void testPlaceOrderNullProduct()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product-missing");
		Assert.assertTrue("testPlaceOrderNullProduct", testResult);
	}
	@Test
	public void testPlaceOrderMinValueProductId()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		p.setProductId(-1);
		oi.setProduct(p );
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product.productId-minvalue");
		Assert.assertTrue("testPlaceOrderMinValueProductId", testResult);
	}

	@Test
	public void testPlaceOrderMaxValueProductId()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		p.setProductId(50001);
		oi.setProduct(p );
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product.productId-maxvalue");
		Assert.assertTrue("testPlaceOrderMaxValueProductId", testResult);
	}

	@Test
	public void testPlaceOrderNullProductName()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		oi.setProduct(p);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product.productName-missing");
		Assert.assertTrue("testPlaceOrderNullProductName", testResult);
	}

	@Test
	public void testPlaceOrderMinLengthProductName()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		p.setProductName("a");
		oi.setProduct(p);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product.productName-minlength");
		Assert.assertTrue("testPlaceOrderMinLengthProductName", testResult);
	}

	@Test
	public void testPlaceOrderMaxLengthProductName()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		p.setProductName("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		oi.setProduct(p);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product.productName-maxlength");
		Assert.assertTrue("testPlaceOrderMaxLengthProductName", testResult);
	}

	@Test
	public void testPlaceOrderInvalidProductName()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		p.setProductName("0123456789");
		oi.setProduct(p);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = baseResponseCheck(placeOrderResponse) && 
				placeOrderResponse.errorDetails.errorReason.contains("orderItems[0].product.productName-invalid");
		Assert.assertTrue("testPlaceOrderInvalidProductName", testResult);
	}

	@Test
	public void testPlaceOrderSuccess()
	{
		Order order = getSuccessOrder();
		AppResponse<String> placeOrderResponse = api.placeOrder(order );
		System.out.println("placeOrderResponse = " + placeOrderResponse);
		boolean testResult = placeOrderResponse.errorDetails == null && 
				placeOrderResponse.responseObject != null && 
				placeOrderResponse.responseObject.equals("111");
		Assert.assertTrue("testPlaceOrderSuccess", testResult);
	}

	@Test
	public void testGetOrderNullArgument()
	{
		String orderId = null;
		AppResponse<Order> getOrderResponse = api.getOrder(orderId );
		System.out.println("getOrderResponse = " + getOrderResponse);
		boolean testResult = baseResponseCheck(getOrderResponse) && 
				getOrderResponse.errorDetails.errorReason.contains("[0]-missing");
		Assert.assertTrue("testGetOrderNullArgument", testResult);
	}

	@Test
	public void testGetOrderNullOrderId()
	{
		String orderId = " ";
		AppResponse<Order> getOrderResponse = api.getOrder(orderId );
		System.out.println("getOrderResponse = " + getOrderResponse);
		boolean testResult = baseResponseCheck(getOrderResponse) && 
				getOrderResponse.errorDetails.errorReason.contains("orderId-missing");
		Assert.assertTrue("testGetOrderNullOrderId", testResult);
	}

	@Test
	public void testGetOrderMinLengthOrderId()
	{
		String orderId = "1";
		AppResponse<Order> getOrderResponse = api.getOrder(orderId );
		System.out.println("getOrderResponse = " + getOrderResponse);
		boolean testResult = baseResponseCheck(getOrderResponse) && 
				getOrderResponse.errorDetails.errorReason.contains("orderId-minlength");
		Assert.assertTrue("testGetOrderMinLengthOrderId", testResult);
	}

	@Test
	public void testGetOrderMaxLengthOrderId()
	{
		String orderId = "01234567890123456";
		AppResponse<Order> getOrderResponse = api.getOrder(orderId );
		System.out.println("getOrderResponse = " + getOrderResponse);
		boolean testResult = baseResponseCheck(getOrderResponse) && 
				getOrderResponse.errorDetails.errorReason.contains("orderId-maxlength");
		Assert.assertTrue("testGetOrderMaxLengthOrderId", testResult);
	}

	@Test
	public void testPlaceBulkOrdersCollNullAgument()
	{
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders((Order[])null);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("[0]-missing");
		Assert.assertTrue("testPlaceBulkOrdersCollNullAgument", testResult);
	}

	@Test
	public void testPlaceBulkOrdersCollMinSizeOrders()
	{
		Order[] orders = new Order[0];
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders-minsize");
		Assert.assertTrue("testPlaceBulkOrdersCollMinSizeOrders", testResult);
	}

	@Test
	public void testPlaceBulkOrdersCollMaxSizeOrders()
	{
		Order[] orders = new Order[4];
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders-maxsize");
		Assert.assertTrue("testPlaceBulkOrdersCollMaxSizeOrders", testResult);
	}

	@Test
	public void testPlaceBulkOrdersCollNullOrder()
	{
		Order[] orders = new Order[1];
		orders[0] = null;
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders[0]-missing");
		Assert.assertTrue("testPlaceBulkOrdersCollNullAgument", testResult);
	}

	@Test
	public void testPlaceBulkOrdersCollNullSecondOrder()
	{
		Order[] orders = new Order[2];
		orders[0] = getSuccessOrder();
		orders[1] = null;
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders[1]-missing");
		Assert.assertTrue("testPlaceBulkOrdersCollNullSecondOrder", testResult);
	}

	@Test
	public void testPlaceBulkOrdersCollSuccess()
	{
		Order[] orders = new Order[2];
		orders[0] = getSuccessOrder();
		orders[1] = getSuccessOrder();
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = placeBulkOrdersResponse.errorDetails == null &&
				placeBulkOrdersResponse.responseObject != null &&
				placeBulkOrdersResponse.responseObject.size() == orders.length;
		Assert.assertTrue("testPlaceBulkOrdersCollSuccess", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapNullAgument()
	{
		AppResponse<List<String>> placeBulkOrdersResponse = api.placeBulkOrders((Map)null);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("[0]-missing");
		Assert.assertTrue("testPlaceBulkOrdersMapNullAgument", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapMinSizeOrders()
	{
		Map<String, Order> orders = new HashMap<String, Order>();
		AppResponse<Void> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders-minsize");
		Assert.assertTrue("testPlaceBulkOrdersMapMinSizeOrders", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapMaxSizeOrders()
	{
		Map<String, Order> orders = new HashMap<String, Order>();
		orders.put("1", getSuccessOrder());
		orders.put("2", getSuccessOrder());
		orders.put("3", getSuccessOrder());
		orders.put("4", getSuccessOrder());
		AppResponse<Void> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders-maxsize");
		Assert.assertTrue("testPlaceBulkOrdersMapMaxSizeOrders", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapMaxLengthSomeKey()
	{
		Map<String, Order> orders = new HashMap<String, Order>();
		orders.put("01234567890", getSuccessOrder());
		AppResponse<Void> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders[01234567890].someKey-maxlength");
		Assert.assertTrue("testPlaceBulkOrdersMapMaxLengthSomeKey", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapNullOrder()
	{
		Map<String, Order> orders = new HashMap<String, Order>();
		orders.put("0123456789", null);
		AppResponse<Void> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders[0123456789].order-missing");
		Assert.assertTrue("testPlaceBulkOrdersMapNullOrder", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapNullOrderItems()
	{
		Map<String, Order> orders = new HashMap<String, Order>();
		Order order = new Order();
		orders.put("0123456789", order );
		AppResponse<Void> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = baseResponseCheck(placeBulkOrdersResponse) && 
				placeBulkOrdersResponse.errorDetails.errorReason.contains("orders[0123456789].order.orderItems-missing");
		Assert.assertTrue("testPlaceBulkOrdersMapNullOrderItems", testResult);
	}

	@Test
	public void testPlaceBulkOrdersMapSuccess()
	{
		Map<String, Order> orders = new HashMap<String, Order>();
		orders.put("0123456789", getSuccessOrder());
		AppResponse<Void> placeBulkOrdersResponse = api.placeBulkOrders(orders);
		System.out.println("placeBulkOrdersResponse = " + placeBulkOrdersResponse);
		boolean testResult = placeBulkOrdersResponse.errorDetails == null;
		Assert.assertTrue("testPlaceBulkOrdersMapSuccess", testResult);
	}

	private Order getSuccessOrder()
	{
		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem oi = new OrderItem();
		oi.setQuantity(1);
		Product p = new Product();
		p.setProductId(1);
		p.setProductName("aaa");
		oi.setProduct(p);
		orderItems.add(oi);
		order.setOrderItems(orderItems );
		return order;
	}

	private boolean baseResponseCheck(AppResponse appResponse)
	{
		return appResponse.errorDetails != null && 
				appResponse.errorDetails.errorType != null && 
				appResponse.errorDetails.errorType.equals("PARAMETER_ERROR") && 
				appResponse.errorDetails.errorReason != null;
	}

}
