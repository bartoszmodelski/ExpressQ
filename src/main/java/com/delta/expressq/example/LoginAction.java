package com.delta.expressq.example;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{
    private static final long serialVersionUID = 1L;
    private Map<String, Object> session;
	private String username;
	private String password;
	
	public String login(){
		if(getUsername() !=null && getUsername().equals(getPassword())){
            session.put("loginId", username);
			return "SUCCESS";
		}else{
			return "ERROR";
	
		}
	}
		
    public String logOut() {
        session.remove("loginId");
        addActionMessage("You have been Successfully Logged Out");
        return SUCCESS;
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
	
    public Map<String, Object> getSession() {
        return session;
    }
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }	
}
	
