<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>

<s:form action="update">
	<s:textfield label="UserID" value="%{#application.UserID}" name="User.UserID" readonly="true"/>
	<s:textfield label="Username" value="%{#application.Username}" name="User.Username" />
	<s:textfield label="Forename" value="%{#application.Fname}" name="User.Fname" />
	<s:textfield label="Surname" value="%{#application.Lname}" name="User.Lname" />
	<s:textfield label="Email" value="%{#application.email}" name="User.email" />
	<s:textfield label="Admin" value="%{#application.Admin}" name="User.Admin" />
	<s:submit value="Update" />
</s:form>
