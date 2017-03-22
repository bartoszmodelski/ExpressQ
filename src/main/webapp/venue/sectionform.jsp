<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>

<s:form name="sectionupdate" method = "POST" action="updatesection"
	<s:textfield name = "section.description" value = "%{#application.description}" label = "New Section Name"/>
	<s:submit value="Update"/>
</s:form>