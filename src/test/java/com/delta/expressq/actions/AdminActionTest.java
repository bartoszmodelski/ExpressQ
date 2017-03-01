package com.delta.expressq.actions;

import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import com.opensymphony.xwork2.ActionProxy;

public class AdminActionTest extends StrutsTestCase{
	
	/**
	 * Tests that the view mapping works as expected
	 */
	public void testGetViewPage() {
		ActionMapping mapping = getActionMapping("/view.action");
		assertNotNull(mapping);
        assertEquals("/", mapping.getNamespace());
        assertEquals("view", mapping.getName());
	}
	
	/**
	 * Test that the insert mapping works as expected
	 */
	public void testGetInsertPage() {
		ActionMapping mapping = getActionMapping("/insert.action");
		assertNotNull(mapping);
        assertEquals("/", mapping.getNamespace());
        assertEquals("insert", mapping.getName());
	}
	
	/**
	 * Tests that the delete operation is not carried out if no user has been selected
	 * @throws Exception
	 */
	public void testDeleteError() throws Exception{
		request.setParameter("arrayDeletionSelection", "");
		ActionProxy proxy = getActionProxy("/del");
        String result = proxy.execute();
        assertEquals("Result returned form executing the action was not error but it should have been.", "error", result);
	}
	
	/**
	 * Tests that edit action returns success when a userID is selected
	 * @throws Exception
	 */
	public void testEditUser() throws Exception{
		request.setParameter("selectedID", "1");
		ActionProxy proxy = getActionProxy("/edit");
        String result = proxy.execute();
        assertEquals("Result returned form executing the action was not success but it should have been.", "success", result);
	}
	
	/**
	 * Tests that edit action returns error when a userID is not selected
	 * @throws Exception
	 */
	public void testEditUserError() throws Exception{
		request.setParameter("selectedID", "");
		ActionProxy proxy = getActionProxy("/edit");
        String result = proxy.execute();
        assertEquals("Result returned form executing the action was not error but it should have been.", "error", result);
	}
}