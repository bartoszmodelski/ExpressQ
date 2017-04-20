package com.delta.swiftq.actions;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;

import com.delta.swiftq.database.*;
import com.delta.swiftq.util.User;
import com.delta.swiftq.util.UserNew;

public class UserProfileAction extends ActionSupportWithSession implements ApplicationAware {
	private static final long serialVersionUID = 1L;
	private String username;
	Map userDetails;
	User user = new User();
	/**
	 * Gets the username from the active session and passes it into UserEdit(), this populates the fields in the jsp file with the current details of the user.
	 */
	public String execute(){
		if (isLoggedIn()){
			UserNew usernew = getUserObject();
			username = usernew.getUsername();
			try {
				ConnectionManager.UserEdit(username, userDetails);		
			} catch (ConnectionManagerException e) {
				return "db_error";
			}
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * This calls a method within ConnectionManager that updates the database with the changes that have been made by the user. It then updates the session so the user can keep using the system.
	 * @return SUCCESS
	 */
	public String Update(){ //session refresh can be improved
		if (isLoggedIn()){
			UserNew usernew = getUserObject();
			username = usernew.getUsername();
			try {
				ConnectionManager.UserUpdate(user);
			} catch (ConnectionManagerException e) {
				return "db_error";
			}
			//refreshes session with new changes
			session.remove("user");
			try {
				UserNew user = ConnectionManager.getUserByUsername(username);
				session.put("user", user);
			} catch (ConnectionManagerException e) {
				return "db_error";
			}
			return SUCCESS;
		}else{
			return ERROR;
		}
	}

	public void setApplication(Map userDetails){
		this.userDetails = userDetails;
	}
	
	public User getUser(){
		return user;
	}
}
