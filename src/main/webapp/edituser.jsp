<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>
<html>
<head>
</head>

<body>
<a href="<s:url action="view.action"/>">Display Records</a>
<br><br>
<!--
<%--   This is -- <s:property value="#application.a" /> --%>

<b>Update Details</b>

<s:form action="updates">

<s:textfield label="UserID" value="%{#application.a}" name="User.UserID" readonly="true"/>
<s:textfield label="Name" value="%{#application.b}" name="mb.nam" />
<s:textfield label="Country" value="%{#application.c}" name="mb.ct" />

<s:submit value="Update" />

</s:form>
-->
</body>
</html>
