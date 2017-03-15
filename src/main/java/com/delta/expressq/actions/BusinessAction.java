package com.delta.expressq.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;

public class BusinessAction extends ActionSupportWithSession implements ServletRequestAware, ApplicationAware {
	public Map<String, Integer> menus = new HashMap<String, Integer>();
	private HttpServletRequest request;
	private String name, description, arrayDeletionSelection[], deleteSelection;
	private int venueid;

	public String Display(){
		try {
			ConnectionManager.setMenus(menus, ConnectionManager.getVenueID(session.get("businessId").toString()));
		} catch (ConnectionManagerException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String NewMenu(){
		return SUCCESS;
	}
	
	public String InsertMenu(){
		try {
			ConnectionManager.InsertMenu(ConnectionManager.getVenueID(session.get("businessId").toString()), name, description);
		} catch (ConnectionManagerException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String Delete(){
		arrayDeletionSelection = request.getParameterValues("deleteSelection");//get values from jsp to pass into connection manager
		System.out.println(Arrays.toString(arrayDeletionSelection));
		if (arrayDeletionSelection == null){
			return ERROR;//maybe change to a redirect with appropriate actionmessage.
		} else {
			try {
				ConnectionManager.DeleteMenus(arrayDeletionSelection);
			} catch (ConnectionManagerException e) {
				return "db_error";
			}	
			return SUCCESS;
		}
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getServletRequest(){
		return request;
	}
	
	public Map<String, Integer> getMap(){
		return menus;
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
	
	public String getDeleteSelection(){
		return deleteSelection;
	}
	
	public void setDeleteSelection(String deleteSelection){
		this.deleteSelection = deleteSelection;
	}

	@Override
	public void setApplication(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
