package com.delta.expressq.actions;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.util.User;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupportWithSession implements ServletRequestAware, ApplicationAware {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private List users = new ArrayList();
	public String arrayDeletionSelection[], deleteSelection, selectedID;
	Map userDetails;
	User user = new User();

	/**
	 * Calls the DisplayUsers method within ConnectionManager to retrieve the users from the database and sets the user object to "disp"
	 * so display.jsp can access the data. 
	 * @return SUCCESS 
	 */
	public String Display(){
		ConnectionManager.DisplayUsers(users);
		request.setAttribute("disp", users);
		return SUCCESS;
	}

	/**
	 * This method gets the values from display.jsp that have been selected for deletion and passes them into the DeleteUsers method
	 * in ConnectionManager.This method will then carry out the required processes to delete the user(s). 
	 * If users have been selected for deletion the program will return an error
	 * @return ERROR if no user has been selected for deletion. SUCCESS if a user has been selected.
	 */
	public String Delete(){
		arrayDeletionSelection = request.getParameterValues("deleteSelection");//get values from jsp to pass into connection manager
		if (arrayDeletionSelection == null){
			return ERROR;//maybe change to a redirect with appropriate actionmessage.
		}
		else{
			ConnectionManager.DeleteUsers(arrayDeletionSelection);
			return SUCCESS;
		}
	}
	
	/**
	 * Retrieves the selectedID from the display.jsp file and calls methods held within connection manager to get the 
	 * userDetails required to populate the fields in the edituser.jsp page.
	 * @return SUCCESS if the UserID selected exists. ERROR if it does not.
	 */
	public String Edit(){
		selectedID = request.getParameter("selectedID");
		if (ConnectionManager.checkUserExists(selectedID) == true){
			ConnectionManager.AdminEditUser(selectedID, userDetails);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * This calls a method within ConnectionManager that updates the database with the changes that have been made by the user.
	 * @return SUCCESS
	 */
	public String UpdateUserDetails(){
		ConnectionManager.AdminUpdateUser(user);
		return SUCCESS;
	}
	
	/**
	 * When the user selects to insert a new user this method returns SUCCESS which is used to redirect the user to the page used to create a new user.
	 * @return SUCCESS
	 */
	public String Insert(){
			return SUCCESS;
	}
	
	//getters and setters
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getServletRequest(){
		return request;
	}
	
	public String getDeleteSelection(){
		return deleteSelection;
	}
	
	public void setDeleteSelection(String deleteSelection){
		this.deleteSelection = deleteSelection;
	}
	
	
	public String getSelectedID(){
		return selectedID;
	}
	
	public void setSelectedID(String selectedID){
		this.selectedID = selectedID;
	}
	
	public void setApplication(Map userDetails){
		this.userDetails = userDetails;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
}
