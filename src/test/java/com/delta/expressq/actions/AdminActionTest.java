package com.delta.expressq.actions;

import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import com.opensymphony.xwork2.ActionProxy;

public class AdminActionTest extends StrutsTestCase{

	public void testGetViewPage() {
		ActionMapping mapping = getActionMapping("/view.action");
		assertNotNull(mapping);
        assertEquals("/", mapping.getNamespace());
        assertEquals("view", mapping.getName());
	}
	
	public void testGetInsertPage() {
		ActionMapping mapping = getActionMapping("/insert.action");
		assertNotNull(mapping);
        assertEquals("/", mapping.getNamespace());
        assertEquals("insert", mapping.getName());
	}
}