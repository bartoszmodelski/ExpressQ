package com.delta.expressq.util;

public class User {
	private int UserID;
	private String Username, Fname, Lname, email, Password;

	public int getUserID(){
		return UserID;
	}
	
	public void setUserID(int UserID){
		this.UserID = UserID;
	}
	
	public String getUsername(){
		return Username;
	}
	
	public void setUsername(String Username){
		this.Username = Username;
	}
	
	public String getFname(){
		return Fname;
	}
	
	public void setFname(String Fname){
		this.Fname = Fname;
	}
	
	public String getLname(){
		return Lname;
	}
	
	public void setLname(String Lname){
		this.Lname = Lname;
	}
	
	public String getPassword(){
		return Password;
	}
	
	public void setPassword(String Password){
		this.Password = Password;
	}
	
	public String getemail(){
		return email;
	}
	
	public void setemail(String email){
		this.email = email;
	}
}