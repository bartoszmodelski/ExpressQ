package com.delta.swiftq.database;

import java.sql.Time;

public class Transaction{
    final public int transactionID;
    final public int userID;
    final public int venueID;
    final public float total;
    final public String date;
    final public int status;
	final public String keywords;
	final public Time collection;
	final public String fullname;
	final public String username;
	
    Transaction(int tID, int uID, int vID, float p, String d, int status, String keywords, Time collection, String username, String fullname){
        this.transactionID = tID;
        this.userID = uID;
        this.venueID = vID;
        this.total = p;
        this.date = d;
		this.status = status;
		this.keywords = keywords;
		this.collection = collection;
		this.username = username;
		this.fullname = fullname;
    }
}
