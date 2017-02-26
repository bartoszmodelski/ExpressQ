package com.delta.expressq.actions;

import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

public class RegistrationTest extends StrutsTestCase{
	public void testGetActionProxy() throws Exception {
        /**
         * Test registration getters
         */
        request.setParameter("Uname", "TestUsername");
        request.setParameter("Fname", "TestFname");
        request.setParameter("Lname", "TestLname");
        request.setParameter("Email", "TestEmail");
        request.setParameter("Pass", "TestPass");
        request.setParameter("PassConf", "TestPassConf");

        ActionProxy proxy = getActionProxy("/reg");
        assertNotNull(proxy);
 
        Registration RegAction = (Registration) proxy.getAction();
        assertNotNull(RegAction);
 
        String result = proxy.execute();
        assertEquals(Action.SUCCESS, result);
        assertEquals("TestUsername", RegAction.getUname());
        assertEquals("TestFname", RegAction.getFname());
        assertEquals("TestLname", RegAction.getLname());
        assertEquals("TestEmail", RegAction.getEmail());
        assertEquals("TestPass", RegAction.getPass());
        assertEquals("TestPassConf", RegAction.getPassConf());
    }
}