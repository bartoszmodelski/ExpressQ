package com.delta.expressq.actions;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private List users = new ArrayList();
	public String arrayDeletionSelection[], deleteSelection;

	public String Display(){
		ConnectionManager.DisplayUsers(users);
		request.setAttribute("disp", users);
		return SUCCESS;
	}

	public String Delete(){
		arrayDeletionSelection = request.getParameterValues("deleteSelection");//get values from jsp to pass into connection manager
		ConnectionManager.DeleteUsers(arrayDeletionSelection);
		return SUCCESS;
	}
	
	public String Edit(){
		ConnectionManager.EditUser();
		return SUCCESS;
	}
	
	public String Insert(){
			return SUCCESS;
	}
	
	//getters and setters
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getServletRequest(){
		return request;
	}
	
	public String getDeleteSelection(){
		return deleteSelection;
	}
	
	public void setDeleteSelection(String deleteSelection){
		this.deleteSelection = deleteSelection;
	}
	
}
