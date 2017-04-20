package com.delta.expressq.actions;

import com.delta.expressq.database.*;
import com.delta.expressq.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import java.time.LocalDate;
import java.sql.Date;


public class Analytics extends ActionSupportWithSession {
	public String execute() {
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				return "menu";
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}


}