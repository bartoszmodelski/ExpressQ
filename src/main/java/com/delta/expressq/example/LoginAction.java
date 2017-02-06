package com.delta.expressq.example;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

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
		addActionMessage("You Have Been Logged Out");//for testing delete later
		return SUCCESS;
	}

	//authenticate and login user
	public String login() {
		if (userName.equals("test") && password.equals("testpassword")) {
			session.put("loginId", userName);
			return SUCCESS;
		} else {
			addActionError("Please Enter Valid Username or Password");//for testing delete later
			return LOGIN;
		}
	}

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

