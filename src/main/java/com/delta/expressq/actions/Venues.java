package com.delta.expressq.actions;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


public class Venues extends ActionSupportWithSession implements ServletRequestAware {
	public HttpServletRequest request;
	public Map<String, Integer> venues = new HashMap<String, Integer>();
	protected Map<String, ArrayList<Item>> items = new HashMap<String, ArrayList<Item>>();
	public String id = null;
	public String venueName;

	/**
	 * Lists the venues for the user to choose, then given a venue choice produces a list of items.
	 */
	public String execute() throws Exception{
		if (id == null) {
			ConnectionManager.setVenues(venues);
			return "listVenues";
		}
		ConnectionManager.setItems(items, id);
		//System.out.println(items.toString());
		if(items.toString() == "{}") { //can be better
			return ERROR;
		}else{
			venueName = ConnectionManager.getVenueName(id);
			return "listItems";
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

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
}
