package com.delta.expressq.database;

public class Venue{
    public int venueID;
    public String name;
    public String address;
    public int phoneNumber;
    public boolean acceptingOrders;

    Venue(int vID, String n, String add, int pN, boolean aO){
        this.venueID = vID;
        this.name = name;
        this.address = add;
        this.phoneNumber = pN;
        this.acceptingOrders = aO;
    }
}
