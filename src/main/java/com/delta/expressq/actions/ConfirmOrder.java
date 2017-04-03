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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

public class ConfirmOrder extends ActionSupportWithSession implements ServletRequestAware{
	public Map<String, String> itemsToOrder = new HashMap<String, String>();
	public List<Item> items = new ArrayList<Item>();
	private int transactionID;
	private String hour, minute, to, body;
	public String keywords = "";
	private HttpServletRequest request;
	private String from = "swiftqdelta@gmail.com";
	private String password = "cs3528deltateam";
	private String subject = "SwiftQ Order Successful";

	static Properties properties = new Properties();
	static{
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
	}
	
	/**
	 * Sends confirmation email when order is successful
	 * @param username 
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmail() throws AddressException, MessagingException{
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication
			getPasswordAuthentication() {
			return new PasswordAuthentication(from, password);
			}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO,
		InternetAddress.parse(to));
		message.setSubject(subject);
		message.setContent("<h1>Order Successful</h1> <p>Thank you for ordering with SwiftQ. Your order number is: "  + Integer.toString(getTransactionID())
		+ "</p>", "text/html");
		Transport.send(message);
	}

	/**
	 * Main function called by struts, when routed to "confirm". Retrives payment details inputted by user and creates a charge to our Stripe account.
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
			to = user.getEmail();
			if (!ActiveRecord.orderExists(username)) {	//if order does not exists
				addDangerMessage("Internal error. ", "Please place order again.");
				return "order_again";
			} else if (!ActiveRecord.isStillValid(username)) { //if order was placed too long ago
				addDangerMessage("Error. ", "Order was not confirmed within allowed time (2 hours). "
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
			
			if ((hour.equals("unspecified")) && (minute.equals("unspecified"))) {
				return placeOrderWithoutTime(username, params);
			} else {
				return placeOrderWithTime(minute, hour, username, params);
			}
		} else {
			addWarningMessage("Hey!", " You have to be logged in to order.");
			return "login_noredirect";
		}
	}



	/**
	 * Place order for specific time. Sends a confirmation email.
	 * @param minute MM in HH:MM time format
	 * @param hour HH in HH:MM time format
	 * @param username user for which order should be placed
	 * @param params
	 * @return "success", "error", "db_error"
	 */
	private String placeOrderWithTime(String minute, String hour, String username, Map<String, Object> params) {
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
		} try{
			sendEmail();
			Charge charge = Charge.create(params);
		}
		catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}finally {
			ActiveRecord.removeOrderFromAR(username);
			return SUCCESS;
		}
	}

	/**
	 * Place order for specific user, without specifying time. Sends a confirmation email.
	 * @param username user for which order should be placed
	 * @param params
	 * @return "success", "db_error"
	 */
	private String placeOrderWithoutTime(String username, Map<String, Object> params) {
		try {
			transactionID = ActiveRecord.confirmOrder(username);
		} catch (ConnectionManagerException e) {
			addActionError("Order not placed.");
			return "db_error";
		}
		try{
			sendEmail();
			Charge charge = Charge.create(params);
		}
		catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}finally {
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
