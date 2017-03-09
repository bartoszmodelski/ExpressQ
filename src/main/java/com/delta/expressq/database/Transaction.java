package com.delta.expressq.database;

import java.sql.Time;

public class Transaction{
    public int transactionID;
    public int userID;
    public int venueID;
    public float total;
    public String date;
    public int status;
	public String keywords;
	public Time collection;
	public String username;
	
    Transaction(int tID, int uID, int vID, float p, String d, int status, String keywords, Time collection, String username){
        this.transactionID = tID;
        this.userID = uID;
        this.venueID = vID;
        this.total = p;
        this.date = d;
		this.status = status;
		this.keywords = keywords;
		this.collection = collection;
		this.username = username;
    }
}
