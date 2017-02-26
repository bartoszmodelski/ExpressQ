package com.delta.expressq.actions;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.util.*;
import java.util.*;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


public class Venues extends ActionSupportWithSession implements ServletRequestAware {
	public HttpServletRequest request;
	public Map<String, Integer> venues = new HashMap<String, Integer>();
	public Map<String, Map<String, ArrayList<Item>>> items = new HashMap<String, Map<String, ArrayList<Item>>>();
	public String id = null;
	
	public String execute() throws Exception{
		if (id == null) {
			ConnectionManager.setVenues(venues);
			return "listVenues";
		} 
		ConnectionManager.setItems(items, id);
		
		//System.out.println(items.toString());
		
		return "listItems";
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, Integer> getMap(){
		return venues;
	}
	
	public Map<String, Map<String, ArrayList<Item>>> getItems() {
		return items;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
}
