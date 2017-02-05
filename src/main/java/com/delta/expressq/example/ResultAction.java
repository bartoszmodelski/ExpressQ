package com.delta.expressq.example;

public class ResultAction {
	private String userName;
	private String pwd;
	
	public String execute(){
		if(getUserName() !=null && getUserName().equals(getPwd())){
			return "SUCCESS";
		}else{
			return "ERROR";
	
		}
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getPwd(){
		return pwd;
	}
	
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	
}
	
