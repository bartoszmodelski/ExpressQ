package com.delta.expressq.actions;


import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.interceptor.SessionAware;
import java.util.*;
import com.delta.expressq.util.*;
import com.delta.expressq.record.*;

public class Summary extends ActionSupportWithSession {
	public Map<String, String> itemsToOrder = new HashMap<String, String>();
	public List<Item> items = new ArrayList<Item>();
	public Order order = new Order();
	private String venue = new String();
	
	public String execute() {
		if (itemsToOrder == null) {
			System.out.println("itemsToOrder is null: implement a try to retrieve it from active record");
		}
		
		//itemsToOrder is a following map [ItemId] = Quantity,
		// thus it has to be converted from String, String to Int, Int
		Map<Integer, Integer>  itemsToOrderConverted = new HashMap<Integer, Integer>();
		int id, quantity;
		for (Map.Entry<String, String> entry : itemsToOrder.entrySet()) {
			
			try {
				id = Integer.parseInt(entry.getKey());
				quantity = Integer.parseInt(entry.getValue());
			} catch (Exception e) {
				return ERROR;
			}
			
			//omit items whose quantity is lower than 1 
			if (quantity > 0) {
				itemsToOrderConverted.put(id, quantity);
			}
		}

		//Retrieve Item objects by IDs
		try {
			items = ConnectionManager.getItemsByIDs(new ArrayList(itemsToOrderConverted.keySet()));
		} catch (Exception e) {
			return ERROR;
		}
		
		for (Item i: items) {
			order.add(i, itemsToOrderConverted.get(i.getID()));
			System.out.println(i.toString() + " " + Integer.toString(itemsToOrderConverted.get(i.getID())));
		}
		
		try {
			order.setVenue(Integer.parseInt(venue));
		} catch (Exception e) {
			return ERROR;
		}
		
		ActiveRecord.setOrderForUser(session.get("loginId").toString(), order);
		return SUCCESS;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
		
	public Order getOrder() {
		return order;
	}
	
	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	public Map<String, String> getItemsToOrder() {
		return itemsToOrder;
	}
	
    public void setItemsToOrder(Map<String, String> order) {
           this.itemsToOrder = itemsToOrder;
    }
	
}