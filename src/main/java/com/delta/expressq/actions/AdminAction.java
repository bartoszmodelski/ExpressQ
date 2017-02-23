package com.delta.expressq.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport implements ServletRequestAware, ApplicationAware{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private List users = new ArrayList();
	public String arrayDeletionSelection[], deleteSelection, fid;
	Map userDetails;

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
		fid = request.getParameter("fid");
		ConnectionManager.EditUser(fid, userDetails);
		System.out.println(userDetails);
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
	
	public void setDeleteSelection(String fid){
		this.fid = fid;
	}
	
	public String getfid(){
		return fid;
	}
	
	public void setfid(String deleteSelection){
		this.deleteSelection = deleteSelection;
	}
	
	public void setApplication(Map userDetails){
		this.userDetails = userDetails;
	}
}
