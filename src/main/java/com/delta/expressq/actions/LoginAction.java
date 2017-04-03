package com.delta.expressq.actions;

import com.delta.expressq.database.*;
import com.delta.expressq.util.UserNew;
import com.delta.expressq.util.BCrypt;

public class LoginAction extends ActionSupportWithSession {
	private static final long serialVersionUID = 1L;
	private String username = "";
	private String password = "";
	public String venueID = "";

	/**
	 * If the user is logged in they are unable to view the log in page.
	 */
	public String execute(){
		if(isLoggedIn()){
			return "permission_error";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * Removes the logged in user from the session.
	 * @return SUCCESS
	 */
	public String logout() {
		if(isLoggedIn()){
			session.remove("user");
			addSuccessMessage("Success! ", "You have been logged out");
			return SUCCESS;
		}else{
			return "permission_error";
		}
	}

	/**
	 * This method calls BCrypt.checkpw to check the plaintext password entered by the user with the hashed password stored in the database. This hashed password is retrieved
	 * by calling getPassword() in connection manager.
	 * If they do match it creates a new session and maps the user to it. If not it displays an error message.
	 * @return LOGIN redirect if the credentials do not match. SUCCESS if they do.
	 */
	public String login() {
		if(isLoggedIn()){
			return "permission_error";
		}else{
			if (username.equals("") || password.equals(""))
				return "login";
			try {
				UserNew user = ConnectionManager.getUserByUsername(username);
				if ((user == null) || !BCrypt.checkpw(password, user.getPassword())) {
					addInformationMessage("Alert! ", "Please enter a valid username and password.");
					return LOGIN;
				} else {
					session.put("user", user);
					if (isRedirectDeclared()) {
						venueID = getRedirectURL();
						removeRedirect();
						return "show_venue";
					} else {
						return SUCCESS;
					}
				}
			} catch (ConnectionManagerException e) {
				return "db_error";
			} catch(IllegalArgumentException ee){
				return "login";
			}
		}
	}

	//Setters and getters for variables
	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVenue(String venueID) {
		this.venueID = venueID;
	}

	public String getVenue() {
		return venueID;
	}
}
