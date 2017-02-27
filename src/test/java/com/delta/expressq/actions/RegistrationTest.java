package com.delta.expressq.actions;

import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import com.opensymphony.xwork2.ActionProxy;

public class RegistrationTest extends StrutsTestCase{
	/**
	 * Tests that a user cannot be registered if it exists
	 * @throws Exception
	 */
	public void testExistedRegistration() throws Exception {

        request.setParameter("Uname", "TestUsername");
        request.setParameter("Fname", "TestFname");
        request.setParameter("Lname", "TestLname");
        request.setParameter("Email", "TestEmail");
        request.setParameter("Pass", "TestPass");
        request.setParameter("PassConf", "TestPass");

        ActionProxy proxy = getActionProxy("/reg");
        assertNotNull(proxy);
 
        Registration RegAction = (Registration) proxy.getAction();
        assertNotNull(RegAction);
 
        String result = proxy.execute();
        assertEquals("TestUsername", RegAction.getUname());
        assertEquals("TestFname", RegAction.getFname());
        assertEquals("TestLname", RegAction.getLname());
        assertEquals("TestEmail", RegAction.getEmail());
        assertEquals("TestPass", RegAction.getPass());
        assertEquals("TestPass", RegAction.getPassConf());
        assertEquals("Result returned from executing the action was not existed but it should have been.", "existed", result);

    }
	
	/**
	 * Tests that registration action maps as expected
	 */
	public void testGetRegisterPage() {
		ActionMapping mapping = getActionMapping("/registration.action");
		assertNotNull(mapping);
        assertEquals("/", mapping.getNamespace());
        assertEquals("registration", mapping.getName());
	}
}