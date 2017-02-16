package com.delta.expressq.example;

import java.util.ArrayList;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;


public class Venues extends ActionSupport{
	private Map<String, Integer> venues = new HashMap<String, Integer>();

	public String execute() throws Exception{
		ConnectionManager.setVenues(venues);
		System.out.println(venues.toString());
		return SUCCESS;
	}
	
	public ArrayList<String> getTable(){
		return new ArrayList<String>(venues.keySet()); 
	}
	
	public Map<String, Integer> getMap(){
		System.out.println(venues.toString());
		return venues;
	}
}
