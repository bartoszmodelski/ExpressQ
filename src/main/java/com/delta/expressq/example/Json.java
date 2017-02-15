package com.delta.expressq.example;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.delta.expressq.database.*;


public class Json extends ActionSupport implements ServletRequestAware {
	private HttpServletRequest request;
	public String name = "";
	public String APIpass = "";
	public String transactionID = "";
	public Transaction trans = null;
	public String xd = "zpdr";
	
	public String execute() {
		//HttpServletRequest request = ServletActionContext.getRequest();
		//trans = ConnectionManager.getTransaction(name, APIpass, Integer.parseInt(transactionID));
		//System.out.println("::" + Float.toString(trans.total));
		return SUCCESS;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getTransID() {
		return Integer.toString(trans.transactionID);
	}
	
	public String getXD() {
		return xd;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
	
}
