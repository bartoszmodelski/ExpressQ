package com.delta.expressq.record;

import java.util.*;
import com.delta.expressq.util.*;
import com.delta.expressq.database.*;

public class ActiveRecord {
	private static Map<String, Order> orders = new HashMap<String, Order>();
	private static long maximalConfirmationTime = 2 * 3600 * 1000; //2hours in millis

	/**
	 * Returns maximal time between adding and confirming order as String. (for errors).
	 * @return String with formatted time.
	 */
	public static String getMaximalConfirmationTimeAsString() {
		return (new java.sql.Timestamp(maximalConfirmationTime)).toString();
	}

	/**
	 * Function assigning given order to user.
	 * @param username username of the client
	 * @param order details of the order
	 */
	public static void setOrderForUser(String username, Order order) {
		orders.put(username, order);
	}

	/**
	 * Checks if there is any order saved for given username
	 * @param username username of client
	 * @return True if exists, false otherwise.
	 */
	public static boolean orderExists(String username) {
		return orders.containsKey(username);
	}

	/**
	 * Check if order is not too old to be confirmed.
	 * @param username username of client who added order
	 * @return True if can be confirmed and false otherwise.
	 */
	public static boolean isStillValid(String username) {
		return orders.get(username).timeFromTimestamp() < maximalConfirmationTime;
	}

	/**
	 * Returns order assigned to user having given username.
	 * @param username username of client who added order
	 * @return Order or null, if Order was not found.
	 */
	public static Order getOrder(String username) {
		if (!orders.containsKey(username))
			return null;
		return orders.get(username);
	}

	/**
	 * Removes order from Active Record structure. Function used after
	 * putting order into database or determining its invalidity.
	 * @param username username of client who added order
	 */
	public static void removeOrderFromAR(String username) {
		if (orders.containsKey(username))
			orders.remove(username);
	}

	/**
	 * Places order assigned to user in database.
	 * @param username username of client who added order
	 * @return TransactionID
	 * @throws ConnectionManagerException
	 */
	public static int confirmOrder(String username) throws ConnectionManagerException {
		return ConnectionManager.newOrder(username, orders.get(username));
	}
}
