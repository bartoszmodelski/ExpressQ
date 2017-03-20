package com.delta.expressq.util;

import java.sql.Timestamp;
import java.util.*;
import com.delta.expressq.record.*;

public class Order {
	private Map<Item, Integer> itemsAndQuantities = new HashMap<Item, Integer>();
	private Timestamp timestamp = null;
	private int venueID = -1;
	private int hour = 0;
	private int minute = 0;
	private String keywords = "";

	public Order() {
		setTimestamp();
	}

	public double getTotal() {
		double total = 0;
		for (Map.Entry entry: itemsAndQuantities.entrySet()){
			 total += ((Integer)(entry.getValue())) * ((Item) (entry.getKey())).getPrice();
		}
		return total;
	}

	public String generateAndSetKeywords() {
		this.keywords = KeywordsGenerator.getKeywords();
		return this.keywords;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setCollectionTime(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}


	public String getCollectionTime() {
		if ((hour == 0) || (minute == 0))
			return "00:00:00";
		return Integer.toString(hour) + ":" + Integer.toString(minute) + ":00";
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

	public void setTimestamp() {
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
