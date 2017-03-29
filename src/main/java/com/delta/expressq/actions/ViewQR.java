package com.delta.expressq.actions;

import java.util.HashMap;
import java.util.Map;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;
import com.delta.expressq.util.UserNew;

public class ViewQR extends ActionSupportWithSession{
	public Map<String, String> orderDetails = new HashMap<String, String>();
	
	public String execute() throws ConnectionManagerException{
		if(isLoggedIn()){
			UserNew user = getUserObject();
			ConnectionManager.getUserTransactions(orderDetails, user.getUserID());
			System.out.println(orderDetails);
			return SUCCESS;
		}else{
			return "permission_error";
		}
	}

	public Map<String, String> getMap(){
		return orderDetails;
	}

}