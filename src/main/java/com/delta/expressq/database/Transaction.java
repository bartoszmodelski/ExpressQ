package com.delta.expressq.database;

public class Transaction{
    public int transactionID;
    public int userID;
    public int venueID;
    public int listID;
    public float total;
    public String date;

    Transaction(int tID, int uID, int vID, int lID, float p, String d){
        this.transactionID = tID;
        this.userID = uID;
        this.venueID = vID;
        this.listID = lID;
        this.total = p;
        this.date = d;
    }
}
