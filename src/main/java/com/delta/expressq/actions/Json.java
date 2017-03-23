package com.delta.expressq.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.delta.expressq.util.Item;
import com.delta.expressq.database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


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


	public String execute() {
		if (!name.isEmpty() && !APIpass.isEmpty() && !transactionID.isEmpty()) {
			return getOneTransaction(name, APIpass, transactionID);
		} else if (!name.isEmpty() && !APIpass.isEmpty() && !minutes.isEmpty()) {
			return getUpcomingOrders(name, APIpass, minutes);
		} else {
			return ERROR;
		}
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

	public String getUpcomingOrders(String name, String APIpass, String minutes) {
		try {
			Map<Transaction, Map<Item, Integer>> orders = ConnectionManager.getUpcomingOrders(name, APIpass, Integer.parseInt(minutes));
			if (!setJSONWithMany(orders))
				return ERROR;
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public boolean setJSONWithMany(Map<Transaction, Map<Item, Integer>> orders) {
		JSONArray obj = new JSONArray();
        try {
			for (Map.Entry<Transaction, Map<Item, Integer>> entry: orders.entrySet()) {
				JSONObject transactionJSON = new JSONObject();
				Transaction trans = entry.getKey();

				transactionJSON.put("orderId", trans.transactionID);
		        transactionJSON.put("username", trans.username);
		        transactionJSON.put("price", trans.total);
		        transactionJSON.put("date", trans.date);
		        transactionJSON.put("status", trans.status);
				transactionJSON.put("collectionTime", trans.collection);
				transactionJSON.put("username", trans.username);
				transactionJSON.put("keywords", trans.keywords);
				transactionJSON.put("fullname", trans.fullname);

				JSONObject items = new JSONObject();
				for (Map.Entry<Item, Integer> itemAndQuantity: entry.getValue().entrySet()) {
					items.put(itemAndQuantity.getKey().getName(), itemAndQuantity.getValue());
				}
				transactionJSON.put("items", items);

				obj.put(transactionJSON);
			}
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
