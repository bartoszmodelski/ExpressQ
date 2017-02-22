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

	public String home() {
		return SUCCESS;
	}

	//log out user
	public String logout() {
		session.remove("loginId");
		addActionMessage("<br>You have been logged out.");//for testing delete later
		return SUCCESS;
	}

	//authenticate and login user
	public String login() {
		if (ConnectionManager.checkCredentials(userName, password) == false) {
			addActionError("<br>Please enter valid username and password.");//for testing delete later
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

