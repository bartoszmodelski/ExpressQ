package com.delta.expressq.actions;
import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.util.BCrypt;

public class Registration extends ActionSupportWithSession {
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
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(Pass, salt);		
		if (ConnectionManager.checkUserName(Uname) == true) { //if the username already exists in the system inform the user. otherwise add the user details to the database
			return "existed";		
		}
		else{
			ConnectionManager.writeUser(Uname, hashed_password, Email, Fname, Lname);
			return "success";
		}

}

}
