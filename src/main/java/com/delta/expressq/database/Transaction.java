package com.delta.expressq.database;

public class Transaction{
    public int transactionID;
    public int userID;
    public int venueID;
    public int listID;
    public float total;
	public boolean issued;
    public String date;

    Transaction(int tID, int uID, int vID, float p, String d, boolean issued){
        this.transactionID = tID;
        this.userID = uID;
        this.venueID = vID;
        this.total = p;
        this.date = d;
		this.issued = issued;
    }
}
