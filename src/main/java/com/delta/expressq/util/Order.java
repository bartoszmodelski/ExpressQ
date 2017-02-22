package com.delta.expressq.util;

import java.sql.Timestamp;
import java.util.*;

public class Order {
	public Map<Item, Integer> itemsAndQuantities;
	public Timestamp timestamp = null; 
	public int venueID = -1;
	
	public Order() {
		itemsAndQuantities = new HashMap<Item, Integer>();
	}
	
	public Order(Map<Item, Integer> itemsAndQuantities) {
		setItemsAndQuantities(itemsAndQuantities);
	}
	
	public void add(Item item, int quantity) {
		itemsAndQuantities.put(item, quantity);
	}
	
	public void setVenue(int venueID) {
		this.venueID = venueID;
	}
	
	public int getVenue() {
		return venueID;
	}
	
	public void setItemsAndQuantities(Map<Item, Integer> itemsAndQuantities) {
		this.itemsAndQuantities = itemsAndQuantities;
	}
	
	public void updateTimestamp() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		timestamp = new java.sql.Timestamp(now.getTime());
	}
	
	public long timeFromTimestamp() {
		if (timestamp == null)
			return Long.MAX_VALUE;
		
		return (long)Calendar.getInstance().getTimeInMillis() - (long)timestamp.getTime();
	}
	
	public Map<Item, Integer> getItemsAndQuantities() {
		return itemsAndQuantities;
	}
}