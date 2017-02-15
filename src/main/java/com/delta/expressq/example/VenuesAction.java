package com.delta.expressq.example;

import java.util.ArrayList;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;


public class VenuesAction extends ActionSupport{
	private ArrayList<String> venueList;

	public String execute() throws Exception{
		venueList = new ArrayList<String>();
		venueList = ConnectionManager.listVenueNames();
		return SUCCESS;
	}
}
