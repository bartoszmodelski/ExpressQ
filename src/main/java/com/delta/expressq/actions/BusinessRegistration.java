package com.delta.expressq.actions;
import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.util.BCrypt;

public class BusinessRegistration extends ActionSupportWithSession {
	private static final long serialVersionUID = 1L;
	private String name, address, pass, phonenumber, PassConf;
	//private int phonenumber;
	
	//Setters and getters for registration variables
	public String getName(){
		return name;}

	public void setName(String name) {
		this.name = name;
}

	public String getPass(){
		return pass;}

	public void setPass(String pass) {
		this.pass = pass;
}

	public String getPhonenumber(){
		return phonenumber;}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
}

	public String getAddress(){
		return address;}

	public void setAddress(String address) {
		this.address = address;
}
	public String getPassConf(){
		return PassConf;
	}
	
	public void setPassConf(String PassConf){
		this.PassConf = PassConf;
	}
	

	/**
	 * When the registration action is called this checks if a user already exists in the database with the username that has been selected by the user. 
	 * If it does already exits then it returns the result existed. If the username does not already exist then the new user details are written to the database.
	 */
	public String execute()throws Exception{
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(pass, salt);		
		System.out.println(name + hashed_password + address + phonenumber);
		ConnectionManager.writeVenue(name, hashed_password, address, phonenumber);
		return "success";

}

}