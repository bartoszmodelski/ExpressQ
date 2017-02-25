package com.delta.expressq.actions;
import com.delta.expressq.database.ConnectionManager;
import com.opensymphony.xwork2.ActionSupport;

public class Registration extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String Uname, Pass, PassConf, Email, Fname, Lname;

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

	/**
	 * When the registration action is called this checks if a user already exists in the database with the username that has been selected by the user. 
	 * If it does already exits then it returns the result existed. If the username does not already exist then the new user details are written to the database.
	 */
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
