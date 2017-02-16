package com.delta.expressq.example;

import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class ConfirmAction extends ActionSupport{
	
	public int lastOrder;
	
	public String execute(){
		//ConnectionManager.newTransaction();
		return SUCCESS;
		//addActionError("Error, please try again");
		//return "fail";
	}
	
	public String qrCode(){
		lastOrder = getID();
		return SUCCESS;
	}
	
	public int getID(){
		lastOrder = ConnectionManager.getLastTransactionID();
		return lastOrder;
	}
	
}