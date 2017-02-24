package com.delta.expressq.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private Map<String, Object> session;

	/**
	 * Used to redirect the user to the home page after they have logged in.
	 * @return SUCCESS
	 */
	public String home() {
		return SUCCESS;
	}

	/**
	 * Removes the logged in user from the session.
	 * @return SUCCESS
	 */
	public String logout() {
		session.remove("loginId");
		addActionMessage("<br>You have been logged out.");//for testing delete later
		return SUCCESS;
	}

	/**
	 * This method calls checkCredentials from connectionManager to validate the login details provided by the user. 
	 * If they do match it creates a new session and maps the user's username to it. If not it displays an error message.
	 * @return LOGIN redirect if the credentials do not match. SUCCESS if they do.
	 */
	public String login() {
		if (ConnectionManager.checkCredentials(userName, password) == false) {
			addActionError("Please enter valid username and password.");//for testing delete later
			return LOGIN;		

		} else {
			session.put("loginId", userName);
			return SUCCESS;
		}
	}

	//Setters and getters for variables
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

}

