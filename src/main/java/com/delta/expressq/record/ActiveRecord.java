package com.delta.expressq.record;

import java.util.*;
import com.delta.expressq.util.*;

public class ActiveRecord {
	private static Map<String, Order> orders = new HashMap<String, Order>();
	
	public static void setOrderForUser(String user, Order order) {
		orders.put(user, order);
		
		System.out.println("Venue: " + Integer.toString(order.getVenue()));
		System.out.println("User: " + user);
	}
	
	public static Order getOrder(String user) {
		if (!orders.containsKey(user))
			return null;
		
		Order order = orders.get(user);
		if (order.timeFromTimestamp() > 900000)
			return null;
		return order;
	}
	
	public static void confirmOrder(String user) {
		//manipualte db
	}
}