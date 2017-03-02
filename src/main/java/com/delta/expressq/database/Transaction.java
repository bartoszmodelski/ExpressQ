package com.delta.expressq.database;

public class Transaction{
    public int transactionID;
    public int userID;
    public int venueID;
    public float total;
    public String date;
    public int status;
	
    Transaction(int tID, int uID, int vID, float p, String d, int status){
        this.transactionID = tID;
        this.userID = uID;
        this.venueID = vID;
        this.total = p;
        this.date = d;
		this.status = status;
    }
}
