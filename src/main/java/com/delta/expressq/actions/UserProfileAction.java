package com.delta.expressq.actions;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.delta.expressq.database.*;
import com.delta.expressq.util.User;
import com.opensymphony.xwork2.ActionSupport;

public class UserProfileAction extends ActionSupportWithSession implements ApplicationAware {
	private static final long serialVersionUID = 1L;
	private String username;
	Map userDetails;
	User user = new User();

	public String execute(){	
		username = session.get("loginId").toString();
		try {
			ConnectionManager.UserEdit(username, userDetails);		
		} catch (ConnectionManagerException e) {
			return "db_error";
		}
		return SUCCESS;
	}
	
	/**
	 * This calls a method within ConnectionManager that updates the database with the changes that have been made by the user.
	 * @return SUCCESS
	 */
	public String Update(){
		try {
			ConnectionManager.UserUpdate(user);
		} catch (ConnectionManagerException e) {
			return "db_error";
		}
		return SUCCESS;
	}
	
	public void Edit(){

	}


	public void setApplication(Map userDetails){
		this.userDetails = userDetails;
	}
	
	public User getUser(){
		return user;
	}
}
