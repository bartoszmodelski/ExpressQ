package com.delta.expressq.actions;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class UserProfileAction extends ActionSupport implements SessionAware, ApplicationAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String username;
	Map userDetails;


	public String execute(){	
		username = session.get("loginId").toString();
		ConnectionManager.UserEdit(username, userDetails);
		MapUtils.debugPrint(System.out, "myMap", userDetails);
		return SUCCESS;
	}
	
	public void Edit(){

	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setApplication(Map userDetails){
		this.userDetails = userDetails;
	}
	
}
