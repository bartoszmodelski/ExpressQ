package com.delta.expressq.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;

public class BusinessAction extends ActionSupportWithSession implements ServletRequestAware, ApplicationAware {
	private String name, description;
	private int venueid;
	
	public String Display(){
		return SUCCESS;
	}

	public String NewMenu(){
		return SUCCESS;
	}
	
	public String InsertMenu(){
		try {
			venueid = ConnectionManager.getVenueID(session.get("businessId").toString());
			ConnectionManager.InsertMenu(venueid, name, description);
		} catch (ConnectionManagerException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public void setApplication(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
