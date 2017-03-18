package com.delta.expressq.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


import com.delta.expressq.database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


/*

		<s:set var="msg" value="getJSON()" />
		<s:property value="msg" />
		<s:property value="%{#action.name}" />*/


public class Json extends ActionSupportWithSession implements ServletRequestAware {
	private HttpServletRequest request;
	public String name = "";
	public String APIpass = "";
	public String transactionID = "";
	public String minutes = "";
	public String json = "";
	public String status = "";
	
	public String execute() {
		//HttpServletRequest request = ServletActionContext.getRequest();
		
		//if any of the credentials is empty, return error
		if (name.isEmpty() || APIpass.isEmpty())
			return ERROR;
		
		if (!status.isEmpty() && !transactionID.isEmpty()) {
			return setTransactionStatus(name, APIpass, transactionID, status);
		} else if (!transactionID.isEmpty()) {
			return getOneTransaction(name, APIpass, transactionID);
		} else if (!minutes.isEmpty()) {
			return getUpcomingTransactions(name, APIpass, minutes);
		} else {
			return ERROR;
		}
	}
	
	public String setTransactionStatus(String name, String APIpass, String transactionID, String status) {
		return "fds";
	}
	
	public String getOneTransaction(String name, String APIpass, String transactionID) {
		try {
			Transaction trans = ConnectionManager.getTransaction(name, APIpass, Integer.parseInt(transactionID));
			if (trans == null)
				return ERROR;
			
			String username = ConnectionManager.getUsername(trans.userID);
			if (username.equals(""))
				return ERROR;
			
			HashMap hmap = ConnectionManager.getItemsInTransaction(Integer.parseInt(transactionID));
			if (hmap == null)
				return ERROR;
			
			if (!setJSONWithOne(trans, username, hmap))
				return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
		
	public String getUpcomingTransactions(String name, String APIpass, String minutes) {
		try {
			List<Integer> IDs = ConnectionManager.getIDsOfUpcomingTransactions(name, APIpass, Integer.parseInt(minutes));
			if (!setJSONWithMany(IDs))
				return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;			
	}
		
	public boolean setJSONWithMany(List<Integer> IDs) {
		JSONObject obj = new JSONObject();
        try {							
			obj.put("success", 1);
			obj.put("ids", IDs);
        } catch (JSONException exception) {
			return false;
        }
		
		json = obj.toString();
		return true;
	}
		
	public boolean setJSONWithOne(Transaction trans, String username, HashMap<String, Integer> hmap) {
		JSONObject obj = new JSONObject();
   
        try {
        	obj.put("orderId", trans.transactionID);
        	obj.put("username", username);
        	obj.put("price", trans.total);
        	obj.put("date", trans.date);
        	obj.put("status", trans.status);
			obj.put("collectionTime", trans.collection);
			obj.put("username", trans.username);
			obj.put("keywords", trans.keywords);
			obj.put("success", 1);
			obj.put("fullname", trans.fullname);
			JSONObject obj2 = new JSONObject();
			for (Map.Entry<String, Integer> entry : hmap.entrySet()) {				
				obj2.put(entry.getKey(), entry.getValue());   
			}
			
			obj.put("items", obj2);			
        } catch (JSONException exception) {
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
