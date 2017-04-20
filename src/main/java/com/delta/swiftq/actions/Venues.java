package com.delta.swiftq.actions;

import com.delta.swiftq.database.ConnectionManager;
import com.delta.swiftq.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


public class Venues extends ActionSupportWithSession implements ServletRequestAware {
	public HttpServletRequest request;
	public Map<String, Integer> venues = new HashMap<String, Integer>();
	protected Map<String, ArrayList<Item>> items = new HashMap<String, ArrayList<Item>>();
	public String id = null;
	public String populateFieldsScript = "";
	public String venueName;

	/**
	 * Lists the venues for the user to choose, then given a venue choice produces a list of items.
	 */
	public String execute() {
		if (isOrderStoredTemp()) {
			Map<String, String> itemsAndQuantities = getOrderTemp();
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry: itemsAndQuantities.entrySet()) {
				sb.append("document.getElementById(\"summary_itemsToOrder_'" +
					entry.getKey() + "'_\").value = " + entry.getValue() + ";\n");
			}
			populateFieldsScript = sb.toString();
			removeOrderTemp();
		}

		try {
			if (id == null) {
				ConnectionManager.setVenues(venues);
				return "listVenues";
			}


			ConnectionManager.setItems(items, id);


			if(items.toString() == "{}") { //can be better
				return ERROR;
			}else{
				venueName = ConnectionManager.getVenueName(id);
				return "listItems";
			}
		} catch (Exception e) {
			return ERROR;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVenueName(){
		return venueName;
	}

	public void setVenueName(String venueName){
		this.venueName = venueName;
	}

	public Map<String, Integer> getMap(){
		return venues;
	}

	public Map<String, ArrayList<Item>> getItems() {
		return items;
	}

	public String getPopulateFieldsScript() {
		return populateFieldsScript;
	}

	public void setPopulateFieldsScript(String populateFieldsScript) {
		this.populateFieldsScript = populateFieldsScript;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
}
