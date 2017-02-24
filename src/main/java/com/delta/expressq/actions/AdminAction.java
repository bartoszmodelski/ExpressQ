package com.delta.expressq.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.util.User;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport implements ServletRequestAware, ApplicationAware{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private List users = new ArrayList();
	public String arrayDeletionSelection[], deleteSelection, selectedID;
	Map userDetails;
	User user = new User();

	public String Display(){
		ConnectionManager.DisplayUsers(users);
		request.setAttribute("disp", users);
		return SUCCESS;
	}

	public String Delete(){
		arrayDeletionSelection = request.getParameterValues("deleteSelection");//get values from jsp to pass into connection manager
		if (arrayDeletionSelection == null){
			return ERROR;
		}
		else{
			ConnectionManager.DeleteUsers(arrayDeletionSelection);
			return SUCCESS;
		}
	}
	
	public String Edit(){
		selectedID = request.getParameter("selectedID");
		if (ConnectionManager.checkUserExists(selectedID) == true){
			ConnectionManager.EditUser(selectedID, userDetails);
			//System.out.println(userDetails);
			MapUtils.debugPrint(System.out, "myMap", userDetails);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	public String UpdateUserDetails(){
		ConnectionManager.UpdateUser(user);
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
	
	
	public String getSelectedID(){
		return selectedID;
	}
	
	public void setSelectedID(String selectedID){
		this.selectedID = selectedID;
	}
	
	public void setApplication(Map userDetails){
		this.userDetails = userDetails;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
}
