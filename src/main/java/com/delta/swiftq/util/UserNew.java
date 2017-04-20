package com.delta.swiftq.util;

public class UserNew {
	private int userID, type;
	private String firstName, lastName, email, username, password;

	public UserNew(int userID, int type, String firstName, String lastName,
				String email, String username, String password) {
		this.userID = userID;
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	//Getters and setter (where applicable) for all fields
	public int getUserID() {
		return userID;
	}

	public int getType() {
		return type;
	}
	public boolean setType(int type) {
		if (-1 < type && type < 3) {
			this.type = type;
			return true;
		} else {
			return false;
		}
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
}
