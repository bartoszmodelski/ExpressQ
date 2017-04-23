package com.delta.swiftq;

import java.util.Map;


public class Order {
    final public String orderId;
    final public String date;
    final public String collectionTime;
    final public Map<Item, Integer> items;
    final public double price;
    final public String username;
    final public int status;
    final public String keywords;
    final public String fullname;
    final public String ordered;

    public Order(String orderId, String date,
                 Map<Item, Integer> items,
                 double price, String username,
                 int status, String collectionTime,
                 String keywords, String fullname,
                 String ordered) {
        this.orderId = orderId;
        this.date = date;
        this.items = items;
        this.price = price;
        this.username = username;
        this.status = status;
        this.collectionTime = collectionTime;
        this.keywords = keywords;
        this.fullname = fullname;
        this.ordered = ordered;
    }

    //@Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Full name: " + fullname + ".");
        sb.append("\nOrder status: " + getStatus() + ".");
        sb.append("\nPrice paid: £" + String.format("%.2f", price) + ".");
        sb.append("\n\nPurchase:");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            sb.append(String.format("\n%3d - %s", entry.getValue(), entry.getKey().getName()));
            entry.getValue();
        }
        return sb.toString();
    }

    public String getStatus() {
        if (status == 0) {
            return "to be issued";
        } else if (status == 1) {
            return "issued";
        } else {
            return "undefined";
        }
    }

    public String toStringDetailed() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Order id: " + orderId);
        sb.append("\nUsername: " + username);
        sb.append("\nPrice paid: £" + String.format("%.2f", price) + ".");

        sb.append("\nFull name: " + fullname + ".");
        sb.append("\nOrder status: " + getStatus() + ".");
        sb.append("\n\nPurchase:");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            sb.append(String.format("\n%3d - %s", entry.getValue(), entry.getKey().getName()));
            entry.getValue();
        }
        return sb.toString();
    }
}
