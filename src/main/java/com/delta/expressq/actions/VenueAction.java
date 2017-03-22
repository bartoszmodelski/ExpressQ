package com.delta.expressq.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;
import com.delta.expressq.util.UserNew;

public class VenueAction extends ActionSupportWithSession implements ServletRequestAware {
	public Map<String, Integer> sections = new HashMap<String, Integer>();
	public HttpServletRequest request;
	private String description, sectionDeleteSelection, arraySectionDeleteSelection[];
	
	public String execute(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try{
					ConnectionManager.setSections(sections, ConnectionManager.getVenueID(user.getUserID()));
				}catch (ConnectionManagerException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				return ERROR;
			}
		}else{
			return ERROR;
		}
	}
	
	public String NewSection(){
		return SUCCESS;
	}
	
	public String InsertSection(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.InsertSection(ConnectionManager.getVenueID(user.getUserID()), description);
				} catch (ConnectionManagerException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				return ERROR;
			}
		}else{
			return ERROR;
		}
	}
	
	public String DeleteSection(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				arraySectionDeleteSelection = request.getParameterValues("sectionDeleteSelection");//get values from jsp to pass into connection manager
				if (arraySectionDeleteSelection == null){
					System.out.println("array is null");
					return ERROR;//change to message perhaps?
				}else{
					try {
						ConnectionManager.DeleteSections(arraySectionDeleteSelection);
					} catch (ConnectionManagerException e) {
						return "db_error";
					}
				}
				return SUCCESS;
			}else{
				return ERROR;
			}
		}else{
			return ERROR;
		}
	}

	public Map<String, Integer> getMap(){
		return sections;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
	
	public String getDescription(){
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSectionDeleteSelection(){
		return sectionDeleteSelection;
	}
	
	public void setSectionDeleteSelection(String sectionDeleteSelection){
		this.sectionDeleteSelection = sectionDeleteSelection;
	}

}