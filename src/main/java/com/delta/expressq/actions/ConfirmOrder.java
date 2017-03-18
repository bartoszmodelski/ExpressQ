package com.delta.expressq.actions;


import com.delta.expressq.database.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.interceptor.SessionAware;
import java.util.*;
import com.delta.expressq.util.*;
import com.delta.expressq.record.*;

public class ConfirmOrder extends ActionSupportWithSession {
	public Map<String, String> itemsToOrder = new HashMap<String, String>();
	public List<Item> items = new ArrayList<Item>();
	private int transactionID;
	private String hour, minute;
	public String keywords = "";
	
	public String execute() {
		if(isLoggedIn()){
			UserNew user = getUserObject();
			String username = user.getUsername();
			if ((hour != "unspecified") && (minute != "unspecified")) {
				int hourConverted;
				int minuteConverted;
				System.out.println("ConfirmOrder.java: values should be compared against hardcoded hours/times, right now 912:244 goes through.");
				try {
					hourConverted = Integer.parseInt(hour);
					minuteConverted = Integer.parseInt(minute);
					
					keywords = KeywordsGenerator.getKeywords();
					ActiveRecord.getOrder(username).setCollectionTime(hourConverted, minuteConverted);
					ActiveRecord.getOrder(username).setKeywords(keywords);
				} catch (Exception e) {
					System.out.println("Failed to convert. Back to normal path of execution: " + e.getMessage());
				}
			}
			try {
				transactionID = ActiveRecord.confirmOrder(username);		
			} catch (ConnectionManagerException e) {
				return "db_error";
			}
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getHour() {
		return hour;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setMinute(String minute) {
		this.minute = minute;
	}
	
	public String getMinute() {
		return minute;
	}
}