package com.delta.expressq.example;

import java.util.ArrayList;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;

import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


public class Venues extends ActionSupport implements ServletRequestAware {
	private HttpServletRequest request;
	private Map<String, Integer> venues = new HashMap<String, Integer>();
	private Map<String, Map<String, Map<String, Double>>> items = new HashMap<String, Map<String, Map<String, Double>>>();
	public String id = null;

	public String execute() throws Exception{
		if (id == null) {
			ConnectionManager.setVenues(venues);
			return "listVenues";
		} 
		ConnectionManager.setItems(items, id);
		System.out.println(items.toString());
		return "listItems";
	}
	
	
	public Map<String, Integer> getMap(){
		return venues;
	}
	
	public Map<String, Map<String, Map<String, Double>>> getItems() {
		return items;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
}
