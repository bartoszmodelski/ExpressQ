package com.delta.expressq.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


import com.delta.expressq.database.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


/*

		<s:set var="msg" value="getJSON()" />
		<s:property value="msg" />
		<s:property value="%{#action.name}" />*/


public class Json extends ActionSupport implements ServletRequestAware {
	private HttpServletRequest request;
	public String name = "";
	public String APIpass = "";
	public String transactionID = "";
	public String json = "";
	
	
	public String execute() {
		//HttpServletRequest request = ServletActionContext.getRequest();
		if (name.isEmpty() || APIpass.isEmpty() || transactionID.isEmpty())
			return ERROR;
		
		Transaction trans = ConnectionManager.getTransaction(name, APIpass, Integer.parseInt(transactionID));
		if (trans == null)
			return ERROR;
		
		String username = ConnectionManager.getUsername(trans.userID);
		if (username.equals(""))
			return ERROR;
		
		HashMap hmap = ConnectionManager.getItemsInTransaction(Integer.parseInt(transactionID));
		System.out.println(hmap.toString());
		
		if (hmap == null)
			return ERROR;
		
		
		if (!setJSON(trans, username, hmap))
			return ERROR;
		
		
		return SUCCESS;
	}
		
	public boolean setJSON(Transaction trans, String username, HashMap<String, Integer> hmap) {
		JSONObject obj = new JSONObject();
   
        try {
        	obj.put("orderId", trans.transactionID);
        	obj.put("username", username);
        	obj.put("price", trans.total);
        	obj.put("date", trans.date);
        	obj.put("status", trans.status);
			obj.put("success", 1);
			
			JSONObject obj2 = new JSONObject();
			for (Map.Entry<String, Integer> entry : hmap.entrySet()) {				
				obj2.put(entry.getKey(), entry.getValue());   
			}
			
			obj.put("items", obj2);			
        } catch (JSONException exception) {
        	System.out.println("Fatal error: JSONObject could not be created.");
        	System.out.println(exception.getMessage());
			return false;
        }
		
		json = obj.toString();
		return true;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
	
}
