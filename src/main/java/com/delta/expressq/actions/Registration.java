package com.delta.expressq.actions;
import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;
import com.delta.expressq.util.BCrypt;
import com.delta.expressq.util.UserNew;

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
		return Pass;
	}

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
	
	public String display(){
		if(isLoggedIn()){
			return "permission_error";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * Calls the registerUser function if the current user does not exist or is an admin user.
	 */
	public String execute() throws Exception{
		if(isLoggedIn()){
			UserNew user = getUserObject();
				if(user.getType() == 2|| user.getType() == 0){
					return "permission_error";
				}else{
					return registerUser();
				}
		}else{
			return registerUser();
		}
	}
	/**
	 * When the registration action is called this checks if a user already exists in the database with the username and email that has been selected by the user.
	 * If it does already exits then it returns the result existed. If the username does not already exist then the new user details are written to the database.
	 * @return
	 * @throws ConnectionManagerException
	 */
	public String registerUser() throws ConnectionManagerException{
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(Pass, salt);
		if (ConnectionManager.checkUserName(Uname) == true) { //if the username already exists in the system inform the user. otherwise add the user details to the database
			addInformationMessage("Alert! ", "That username already exists.");
			return "registration";
		}
		else if (ConnectionManager.checkEmail(Email) == true){
			addInformationMessage("Alert! ", "That email already exists.");
			return "registration";
		}
		else{
			ConnectionManager.writeUser(Uname, hashed_password, Email, Fname, Lname);
			addSuccessMessage("Registration Successful! ", "Please log in.");
			return "success";
		}
	}
}
