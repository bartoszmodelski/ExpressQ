package com.delta.expressq.actions;

import com.delta.expressq.util.UserNew;

public class VenueAction extends ActionSupportWithSession {
	public String execute(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				return SUCCESS;
			}else{
				return ERROR;
			}
		}else{
			return ERROR;
		}
	}
}