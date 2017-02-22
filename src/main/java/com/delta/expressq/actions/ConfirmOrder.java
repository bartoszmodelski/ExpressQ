package com.delta.expressq.actions;


import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.interceptor.SessionAware;
import java.util.*;
import com.delta.expressq.util.*;
import com.delta.expressq.record.*;

public class ConfirmOrder extends ActionSupport implements SessionAware {
	public Map<String, String> itemsToOrder = new HashMap<String, String>();
	public List<Item> items = new ArrayList<Item>();
	public Order order = new Order();
	private Map<String, Object> session;
	
	public String execute() {
		ActiveRecord.confirmOrder(session.get("loginId").toString());
		return SUCCESS;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
}