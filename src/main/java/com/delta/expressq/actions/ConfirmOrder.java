package com.delta.expressq.actions;

import com.delta.expressq.database.*;
import java.util.*;
import com.delta.expressq.util.*;
import com.delta.expressq.record.*;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

public class ConfirmOrder extends ActionSupportWithSession implements ServletRequestAware{
	public Map<String, String> itemsToOrder = new HashMap<String, String>();
	public List<Item> items = new ArrayList<Item>();
	private int transactionID;
	private String hour, minute;
	public String keywords = "";
	private HttpServletRequest request;

	
	/**
	 * Main function called by struts, when routed to "confirm".
	 * @return "success", "db_error", "login", "error"
	 * @throws APIException 
	 * @throws CardException 
	 * @throws APIConnectionException 
	 * @throws InvalidRequestException 
	 * @throws AuthenticationException 
	 */
	public String execute() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		//if logged in attempt at placing order
		if(isLoggedIn()){
			UserNew user = getUserObject();
			String username = getUserObject().getUsername();

			if (!ActiveRecord.orderExists(username)) {	//if order does not exists
				addActionError("Internal error. Please place order again.");
				return "order_again";
			} else if (!ActiveRecord.isStillValid(username)) { //if order was placed too long ago
				addActionError("Order was not confirmed within allowed time (2 hours). "
					+ "Please place it again.");
				ActiveRecord.removeOrderFromAR(username);
				return "order_again";
			}
			//Setup for stripe charge
			Stripe.apiKey = "sk_test_U1DddsCH9sv1xbGdcv1G7ZRl";
			String token = request.getParameter("stripeToken");
			
			System.out.println(ActiveRecord.getMaximalConfirmationTimeAsString());
			Order order = ActiveRecord.getOrder(username);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", order.getAmount());
			params.put("currency", "gbp");
			params.put("description", user.getFirstName() + " " + user.getLastName() + " ordered from venue with id" + order.getVenue());
			params.put("source", token);
			Charge charge = Charge.create(params);
			if ((hour.equals("unspecified")) && (minute.equals("unspecified"))) {
				return placeOrderWithoutTime(username);
			} else {
				return placeOrderWithTime(minute, hour, username);
			}
		}
		//if not logged redirect to login page
		addActionError("You have to be logged in to place order.");
		return "login";
	}



	/**
	 * Place order for specific time.
	 * @param minute MM in HH:MM time format
	 * @param hour HH in HH:MM time format
	 * @param username user for which order should be placed
	 * @return "success", "error", "db_error"
	 */
	private String placeOrderWithTime(String minute, String hour, String username) {
		int hourConverted, minuteConverted;
		try {
			hourConverted = convertHour(hour);
			minuteConverted = convertMinute(hour);
		} catch (Exception e) {
			addActionError("Incorrect time.");
			return ERROR;
		}

		ActiveRecord.getOrder(username).setCollectionTime(hourConverted, minuteConverted);
		keywords = ActiveRecord.getOrder(username).generateAndSetKeywords();
		try {
			transactionID = ActiveRecord.confirmOrder(username);
		} catch (ConnectionManagerException e) {
			addActionError("Order not placed.");
			return "db_error";
		} finally {
			ActiveRecord.removeOrderFromAR(username);
			return SUCCESS;
		}
	}

	/**
	 * Place order for specific user, without specifying time.
	 * @param username user for which order should be placed
	 * @return "success", "db_error"
	 */
	private String placeOrderWithoutTime(String username) {
		try {
			transactionID = ActiveRecord.confirmOrder(username);
		} catch (ConnectionManagerException e) {
			addActionError("Order not placed.");
			return "db_error";
		} finally {
			ActiveRecord.removeOrderFromAR(username);
			return SUCCESS;
		}
	}

	/**
	 * Function converting String to Integer. Accepts only natural numbers
	 * between 8 and 19 (exclusive), otherwise throws Exception.
	 * @param hour Integer number saved in String.
	 * @return "success", "db_error"
	 * @throws Exception
	 */
	private int convertHour(String hour) throws Exception {
		int converted = Integer.parseInt(hour);
		if ((converted < 8) || (19 < converted))
			throw new Exception("Incorrect hours.");
		return converted;
	}

	/**
	 * Function converting String to Integer. Accepts only natural numbers
	 * between -1 and 60 (exclusive), otherwise throws Exception.
	 * @param hour Integer number saved in String.
	 * @return "success", "db_error"
	 * @throws Exception
	 */
	private int convertMinute(String minute) throws Exception {
		int converted = Integer.parseInt(minute);
		if ((converted < 0) || (59 < converted))
			throw new Exception("Incorrect minutes.");
		return converted;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getHour() {
		return hour;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getMinute() {
		return minute;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getServletRequest(){
		return request;
	}
}
