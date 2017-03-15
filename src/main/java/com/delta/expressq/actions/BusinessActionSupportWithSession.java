package com.delta.expressq.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class BusinessActionSupportWithSession extends ActionSupport implements SessionAware {
	protected Map<String, Object> session;
	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
	
	public boolean isLoggedIn() {
		return session.containsKey("loginId");
	}
}