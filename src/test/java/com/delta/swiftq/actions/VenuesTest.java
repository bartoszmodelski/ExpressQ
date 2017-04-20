package com.delta.swiftq.actions;

import org.apache.struts2.StrutsTestCase;

import com.opensymphony.xwork2.ActionProxy;

 public class VenuesTest extends StrutsTestCase{
	 /**
	  * Tests that if the venueID entered does not exist that an error message is returned.
	 * @throws Exception 
	  */
	 public void testVenueItemsError() throws Exception {
		 request.setParameter("id", "0");
		 ActionProxy proxy = getActionProxy("/venues");
		 String result = proxy.execute();
	     assertEquals("Result returned form executing the action was not error but it should have been.", "error", result);
	}
	 /**
	  * Tests that listItems is returned when a valid venueID is provided
	  * @throws Exception
	  */
	 public void testVenueItemsSuccess() throws Exception {
		 request.setParameter("id", "22");
		 ActionProxy proxy = getActionProxy("/venues");
		 String result = proxy.execute();
	     assertEquals("Result returned form executing the action was not listItems but it should have been.", "listItems", result);
	}
	
	/**
	 * Tests that when VenueID has value null listVenues is returned
	 * @throws Exception
	 */
	public void testVenue() throws Exception{
		request.setAttribute("id", null);
		ActionProxy proxy = getActionProxy("/venues");
		String result = proxy.execute();
	    assertEquals("Result returned form executing the action was not listVenues but it should have been.", "listVenues", result);
	}
 }
