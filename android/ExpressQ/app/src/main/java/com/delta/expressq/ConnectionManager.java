package com.delta.expressq;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ConnectionManager {
    private final String url, username, APIkey;
    private final String minutes = "30";
    private final MainActivity activity;
    private final Method replyWithUpcomingOrders, processCheckCredentialsReplyMethod,
        replyWithOrder, processOrderReplyMethod, processUpcomingOrdersReplyMethod,
        replyWithCorrectCredentials;

    public ConnectionManager(String url, String username, String APIkey, MainActivity activity,
                             Method replyWithUpcomingOrders, Method replyWithOrder,
                             Method replyWithCorrectCredentials) throws NoSuchMethodException {
        this.url = url;
        this.username = username;
        this.APIkey = APIkey;
        this.activity = activity;
        this.replyWithOrder = replyWithOrder;
        this.replyWithUpcomingOrders = replyWithUpcomingOrders;
        this.replyWithCorrectCredentials = replyWithCorrectCredentials;

        processOrderReplyMethod = ConnectionManager.class.getMethod("processOrderReply", String.class);
        processUpcomingOrdersReplyMethod = ConnectionManager.class.getMethod("processUpcomingOrdersReply", String.class);
        processCheckCredentialsReplyMethod = ConnectionManager.class.getMethod("processCheckCredentialsReply", String.class);
    }

    private void showError(String error) {
        activity.showToast(error);
    }



    public void requestUpcomingOrders() {
        requestHttp(url + "?name=" + username + "&APIpass=" + APIkey + "&minutes=" + minutes,
                processUpcomingOrdersReplyMethod);
    }

    public void requestUpdateStatus(String transactionID, String status) {
        requestHttp(url + "?name=" + username + "&APIpass=" + APIkey
                + "&transactionID=" + transactionID + "&status=" + status,
                null);
    }

    public void requestCheckCredentials() {
        requestHttp(url + "?name=" + username + "&APIpass=" + APIkey,
                processCheckCredentialsReplyMethod);
    }

    public void processCheckCredentialsReply(String response) {
        if (response.equals("incorrect_credentials")){
            showError("Incorrect credentials.");
        } else if (response.equals("correct_credentials")) {
            try {
                replyWithCorrectCredentials.invoke(activity);
            } catch (Exception e) {
                showError("Unknown error. If it persists please contact SwiftQ team.");
            }
        } else {
            showError("Unknown error. If it persists please contact SwiftQ team.");
        }
    }

    public void processUpcomingOrdersReply(String response) {
        try {
            JSONArray jObject = new JSONArray(response);
            List<Order> orders = new ArrayList<Order>();
            for (int i = 0; i < jObject.length(); i++) {
                JSONObject order = jObject.getJSONObject(i);

                String orderID = order.getString("orderId");
                double price = order.getDouble("price");
                String keywords = order.getString("keywords");
                String fullname = order.getString("fullname");
                String user = order.getString("username");
                String collectionTime = order.getString("collectionTime");
                int status = order.getInt("status");
                String ordered = order.getString("date");
                String date = order.getString("date");

                Map<Item, Integer> itemsAndQuantities = new HashMap<Item, Integer>();
                JSONObject itemsAndQuantitiesJSON = order.getJSONObject("items");
                Iterator<String> item = itemsAndQuantitiesJSON.keys();
                String curr = "";
                while (item.hasNext()) {
                    curr = item.next();
                    itemsAndQuantities.put(new Item(curr), itemsAndQuantitiesJSON.getInt(curr));
                }
                orders.add(new Order(orderID, date,
                        itemsAndQuantities,
                        price, user, status,
                        collectionTime, keywords,
                        fullname, ordered));
            }
            replyWithUpcomingOrders.invoke(activity, orders);
        } catch (JSONException e) {
            showError("Incorrect response from server. If problems persists, please contact SwiftQ team.");

        } catch (Exception e) {
            // When user decides to scan before this finished invoking will throw an Exception.
            // Everything's alright, no need inform anyone.
        }
    }

    public void requestOrder(String qrcode) {
        requestHttp(url + "?name=" + username + "&APIpass=" + APIkey + "&transactionID=" + qrcode,
                        processOrderReplyMethod);
    }

    public void processOrderReply(String response) {
        try {
            JSONObject order = new JSONObject(response);

            String orderId =  order.getString("orderId");
            String date =  order.getString("date");

            Map<Item, Integer> itemsAndQuantities = new HashMap<Item, Integer>();
            JSONObject itemsAndQuantitiesJSON =  order.getJSONObject("items");
            Iterator<String> item = itemsAndQuantitiesJSON.keys();
            String curr = "";
            while (item.hasNext()) {
                curr = item.next();
                itemsAndQuantities.put(new Item(curr), itemsAndQuantitiesJSON.getInt(curr));
            }

            double price =  order.getDouble("price");
            String user =  order.getString("username");
            int status =  order.getInt("status");
            String keywords =  order.getString("keywords");
            String fullname =  order.getString("fullname");
            String collectionTime =  order.getString("collectionTime");
            String ordered =  order.getString("date");
            replyWithOrder.invoke(activity,
                    new Order(orderId, date, itemsAndQuantities,
                            price, user, status, collectionTime, keywords,
                            fullname, ordered));
        } catch (JSONException e) {
            showError("Incorrect response from server. If problems persists, please contact SwiftQ team.");
        } catch (Exception e) {
            showError("Incorrect response from server. If problems persists, please contact SwiftQ team.");
        }
    }

    private void invokeMethod(final Method callback, Object argument) {
        try {
            if (argument == null) {
                callback.invoke(this);
            } else
                callback.invoke(this, argument);
        } catch (IllegalAccessException e) {
            showError("IAE Java Reflection Error: please contact SwiftQ team. " +
                    "Content of exception: " + e.getMessage());
        } catch (InvocationTargetException e) {
            showError("ITE Java Reflection Error: please contact SwiftQ team. " +
                    "Content of exception: " + e.getMessage());
        }
    }

    private void requestHttp(final String finalURL, final Method callback) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String responseFormatted = response.toString().replaceAll("&quot;", "\"");

                        //If response is incorrect print message and omit callback
                        if (responseFormatted.equals("incorrect_credentials")) {
                            showError("Incorrect credentials");
                        } else if (responseFormatted.equals("incorrect_id")) {
                            showError("Incorrect QR code");
                        } else if (responseFormatted.equals("unknown_error")) {
                            showError("Unknown error. If it persists contact SwiftQ team.");
                        } else if (callback != null) {
                                invokeMethod(callback, responseFormatted);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError("Failed to connect to SwiftQ services.");
                    }
                }) {
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        queue.add(stringRequest);
    }
}
