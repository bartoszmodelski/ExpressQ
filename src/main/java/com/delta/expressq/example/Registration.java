package com.delta.expressq.example;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class Registration extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String Uname;
	private String Pass;
	private String PassConf;
	private String Email;
	private String Fname;
	private String Lname;

	
	//Setters and getters for registration variables
	public String getUname(){
		return Uname;}

	public void setUname(String Uname) {
		this.Uname = Uname;
}

	public String getPass(){
		return Pass;}

	public void setPass(String Pass) {
		this.Pass = Pass;
}

	public String getPassConf(){
		return PassConf;
	}
	
	public void setPassConf(String PassConf){
		this.PassConf = PassConf;
	}
	
	public String getEmail(){
		return Email;}

	public void setEmail(String Email) {
		this.Email = Email;
}

	public String getFname(){
		return Fname;}

	public void setFname(String Fname) {
		this.Fname = Fname;
}

	public String getLname(){
		return Lname;}

	public void setLname(String Lname) {
		this.Lname = Lname;
}

	public String execute()throws Exception{
		//if the username already exists in the system inform the user. otherwise add the user details to the database
		if (ConnectionManager.checkUserName(Uname) == true) {
			return "existed";		
		}
		else{
			ConnectionManager.writeUser(Uname, Pass, Email, Fname, Lname);
			return "success";
		}

}

}
