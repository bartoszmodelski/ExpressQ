package com.delta.expressq.database;

public class Transaction{
    public int transactionID;
    public int userID;
    public int venueID;
    public float total;
	public boolean issued;
    public String date;
    public boolean issued;

<<<<<<< HEAD
    Transaction(int tID, int uID, int vID, float p, String d, boolean i){
=======
    Transaction(int tID, int uID, int vID, float p, String d, boolean issued){
>>>>>>> json-api
        this.transactionID = tID;
        this.userID = uID;
        this.venueID = vID;
        this.total = p;
        this.date = d;
<<<<<<< HEAD
        this.issued = i;
=======
		this.issued = issued;
>>>>>>> json-api
    }
}
