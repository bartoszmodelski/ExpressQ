package com.delta.expressq.actions;


import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.interceptor.SessionAware;

import java.text.DecimalFormat;
import java.util.*;
import com.delta.expressq.util.*;
import com.delta.expressq.record.*;

public class Summary extends ActionSupportWithSession {
	public Map<String, String> itemsToOrder = new HashMap<String, String>();
	public List<Item> items = new ArrayList<Item>();
	public Order order = new Order();
	private String venue = new String();
	public Double total;
	public String amount;

	/**
	 * Gathers the items the user has placed for order and displays it.
	 */
	public String execute() {
		if(isLoggedIn()){
			UserNew user = getUserObject();
			if (itemsToOrder == null) {
				System.out.println("itemsToOrder is null: implement a try to retrieve it from active record");
				return ERROR;
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
			if(items.isEmpty()){
				return "empty";
			}else{
				for (Item i: items) {
					order.add(i, itemsToOrderConverted.get(i.getID()));
					System.out.println(i.toString() + " " + Integer.toString(itemsToOrderConverted.get(i.getID())));
				}

				try {
					order.setVenue(Integer.parseInt(venue));
				} catch (Exception e) {
					return ERROR;
				}
				ActiveRecord.setOrderForUser(user.getUsername(), order);
				return SUCCESS;
			}
		} else {
			declareRedirectAfterLogin(venue);
			storeOrderTemp(itemsToOrder);
			addWarningMessage("Hey!", " You have to be logged in to order.");
			return "login_noredirect";
		}
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

    public Double getTotal(){
    	total = order.getTotal();
    	return total;
    }

    public void setTotal(Double total){
    	this.total = total;
    }

    public String getAmount(){
    	total = order.getTotal();
    	DecimalFormat df = new DecimalFormat("#.00");
    	amount = df.format(total);
    	amount = amount.replace(".","");
    	return amount;
    }

    public void setAmount(String amount){
    	this.amount = amount;
    }

}
