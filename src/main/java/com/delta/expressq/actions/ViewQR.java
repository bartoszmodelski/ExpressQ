package com.delta.expressq.actions;

import java.util.HashMap;
import java.util.Map;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;
import com.delta.expressq.util.UserNew;

public class ViewQR extends ActionSupportWithSession{
	public Map<String, String> orderDetails = new HashMap<String, String>();
	
	/**
	 * Displays the users current active orders so they can view their qrcode(s) again.
	 * @return Success if user is logged in, permission error if they are not
	 */
	public String execute() throws ConnectionManagerException{
		if(isLoggedIn()){
			UserNew user = getUserObject();
			ConnectionManager.getUserTransactions(orderDetails, user.getUserID());
			if (orderDetails.isEmpty()){
				return "empty";
			}else{
				return SUCCESS;
			}
		}else{
			return "permission_error";
		}
	}

	public Map<String, String> getMap(){
		return orderDetails;
	}

}