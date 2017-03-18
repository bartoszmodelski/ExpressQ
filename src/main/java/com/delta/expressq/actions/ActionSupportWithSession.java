package com.delta.expressq.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import com.delta.expressq.util.UserNew;

public class ActionSupportWithSession extends ActionSupport implements SessionAware {
	protected Map<String, Object> session;

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> map) {
		this.session = map;

		//if (isLoggedIn())
			//System.out.println(getUserObject().getUsername());
	}

	public boolean isLoggedIn() {
		return session.containsKey("user");
	}

	public UserNew getUserObject() {
		return (UserNew)session.get("user");
	}
}
