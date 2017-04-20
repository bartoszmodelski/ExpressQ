package com.delta.swiftq.actions;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.delta.swiftq.database.*;
import com.delta.swiftq.util.BCrypt;
import com.delta.swiftq.util.User;
import com.delta.swiftq.util.UserNew;

public class AdminAction extends ActionSupportWithSession implements ServletRequestAware, ApplicationAware {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private List users = new ArrayList();
	public String arrayDeletionSelection[], deleteSelection, selectedID;
	Map userDetails;
	User user = new User();
	private String username, password, passwordConf, email, fName, lName;
	private int type;

	/**
	 * Calls the DisplayUsers method within ConnectionManager to retrieve the users from the database and sets the user object to "disp"
	 * so display.jsp can access the data. 
	 * @return SUCCESS if an admin user is trying to access the page, otherwise return "permission_error".
	 */
	public String Display(){
		if(isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				try {
					ConnectionManager.DisplayUsers(users);
				} catch (ConnectionManagerException e) {
					return "db_error";
				}
				request.setAttribute("disp", users);
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}

	/**
	 * This method gets the values from display.jsp that have been selected for deletion and passes them into the DeleteUsers method
	 * in ConnectionManager.This method will then carry out the required processes to delete the user(s). 
	 * If no users have been selected for deletion the function will return an error
	 * @return ERROR if no user has been selected for deletion. SUCCESS if a user has been selected. "permission_error" if the user trying to access the method is not an admin.
	 */
	public String Delete(){
		if (isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				arrayDeletionSelection = request.getParameterValues("deleteSelection");//get values from jsp to pass into connection manager
				if (arrayDeletionSelection == null){
					return ERROR;//maybe change to a redirect with appropriate actionmessage.
				} else {
					try {
						ConnectionManager.DeleteUsers(arrayDeletionSelection);
					} catch (ConnectionManagerException e) {
						return "db_error";
					}
					addSuccessMessage("Success! ", "User(s) Deleted.");
					return SUCCESS;
				}
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	/**
	 * Retrieves the selectedID from the display.jsp file and calls methods held within connection manager to get the 
	 * userDetails required to populate the fields in the edituser.jsp page.
	 * @return SUCCESS if the UserID selected exists. ERROR if it does not. "permission_error" if the user trying to access the method is not an admin.
	 */
	public String Edit(){
		if(isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				selectedID = request.getParameter("selectedID");
				try {
					if (ConnectionManager.checkUserExists(selectedID) == true) {
						ConnectionManager.AdminEditUser(selectedID, userDetails);
						return SUCCESS;
					} else {
						return ERROR;
					}
				} catch (ConnectionManagerException e) {
					return "db_error";
				}
			}else {
				return "permission_error";
			}
		}else
			return "permission_error";
	}
	
	/**
	 * This calls a method within ConnectionManager that updates the database with the changes that have been made by the user.
	 * @return SUCCESS if an admin user is trying to access the page, otherwise return "permission_error".
	 */
	public String UpdateUserDetails(){
		if(isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				try {
					ConnectionManager.AdminUpdateUser(user);
				} catch (ConnectionManagerException e) {
					System.out.println(e.getMessage());
					return "db_error";
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	/**
	 * When the user selects to insert a new user this method returns SUCCESS which is used to redirect the user to the page used to create a new user.
	 * @return SUCCESS if an admin user is trying to access the page, otherwise return "permission_error".
	 */
	public String Insert(){
		if(isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	/**
	 * Displays admin version of the registration page. This page allows the admin to specify the user type.
	 * @return
	 */
	public String InsertDisplay(){
		if(isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String InsertUser() throws ConnectionManagerException{
		if(isLoggedIn()){
			UserNew adminuser = getUserObject();
			if (adminuser.getType() == 1){
				String salt = BCrypt.gensalt(12);
				String hashed_password = BCrypt.hashpw(password, salt);
				if (ConnectionManager.checkUserName(username) == true) { //if the username already exists in the system inform the user. otherwise add the user details to the database
					addInformationMessage("Alert! ", "That username already exists.");
					return "fail";
				}
				else if (ConnectionManager.checkEmail(email) == true){
					addInformationMessage("Alert! ", "That email already exists.");
					return "fail";
				}
				else{
					ConnectionManager.adminWriteUser(username, hashed_password, email, fName, lName, type);
					addSuccessMessage("Registration Successful! ", "");
					return "success";
				}
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
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
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPasswordConf(){
		return passwordConf;
	}
	
	public void setPasswordConf(String passwordConf){
		this.passwordConf = passwordConf;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getFName(){
		return fName;
	}
	
	public void setFName(String fName){
		this.fName = fName;
	}
	
	public String getLName(){
		return lName;
	}
	
	public void setLName(String lName){
		this.lName = lName;
	}
	
	public int getType(){
		return type;
	}
	
	public void setType(int type){
		this.type = type;
	}
}
