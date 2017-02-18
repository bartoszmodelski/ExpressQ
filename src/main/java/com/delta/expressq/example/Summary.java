package com.delta.expressq.example;

import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import java.util.*;

public class Summary extends ActionSupport {
	public Map<String, String> order = new HashMap<String, String>();
	
	public String execute() {
		
		//converting ArrayList of Strings to Integers and filtering out all items with quantity = 0
		ArrayList<Integer> IDs = new ArrayList<Integer>(); 
		int buffer = 0;
		for (Map.Entry<String, String> entry : order.entrySet()) {
			try {
				buffer = Integer.parseInt(entry.getKey());
				if (Integer.parseInt(entry.getValue()) > 0)
					IDs.add(buffer);
			} catch (Exception e) {
				return ERROR;
			} 
		}
		System.out.println(order.toString());
		System.out.println(ConnectionManager.getItemsByIDs(IDs).toString());
		
		
		return SUCCESS;
	}


    public Map<String, String> getOrder() {
           return this.order;
    }

    public void setPlanMap(Map<String, String> order) {
           this.order = order;
    }
}