package com.delta.expressq.example;

import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class ConfirmAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	public int lastOrder;
	
	public String execute(){
		//ConnectionManager.newTransaction();
		return SUCCESS;
		//addActionError("Error, please try again");
		//return "fail";
	}
	
	//assign id to variable to pass into jsp file
	public String qrCode(){
		lastOrder = getID();
		return SUCCESS;
	}
	
	//retrieve the id of the last transaction from the database
	public int getID(){
		lastOrder = ConnectionManager.getLastTransactionID();
		return lastOrder;
	}
	
}