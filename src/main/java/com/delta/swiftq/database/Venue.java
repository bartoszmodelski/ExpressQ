package com.delta.swiftq.database;

public class Venue{
    public int venueID;
    public String name;
    public String address;
    public int phoneNumber;
    public boolean acceptingOrders;

    Venue(int vID, String n, String add, int pN, boolean aO){
        this.venueID = vID;
        this.name = name;//IS THIS REQUIRED??
        this.address = add;
        this.phoneNumber = pN;
        this.acceptingOrders = aO;
    }
}
