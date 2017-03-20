package com.delta.expressq.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import com.delta.expressq.util.UserNew;

public class ActionSupportWithSession extends ActionSupport implements SessionAware {
	protected Map<String, Object> session;
 	protected Map<String, List<String>> messages = new HashMap<String, List<String>>();

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

	public Map<String, List<String>> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, List<String>> map) {
		this.messages = map;
	}


	private void addMessage(String type, String message) {
		if (!messages.containsKey(type))
			messages.put(type, new ArrayList<String>());
		messages.get(type).add(message);
	}

	public void addInformationMessage(String strong, String rest) {
		addMessage("info", "<strong>" + strong + "</strong>" + rest);
	}

	public void addSuccessMessage(String strong, String rest) {
		addMessage("success", "<strong>" + strong + "</strong>" + rest);
	}

	public void addDangerMessage(String strong, String rest) {
		addMessage("danger", "<strong>" + strong + "</strong>" + rest);
	}

	public void addWarningMessage(String strong, String rest) {
		addMessage("warning", "<strong>" + strong + "</strong>" + rest);
	}
}
