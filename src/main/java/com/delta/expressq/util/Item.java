package com.delta.expressq.util;

public class Item {
	public double price;
	public String name;
	public int ID;
	
	public Item(double price, String name, int ID) {
		this.price = price;
		this.name = name;
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public double getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
		return "ID: " + Integer.toString(ID) + " - " + name;
	}
}