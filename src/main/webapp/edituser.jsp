<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>
<t:wrapper>
	<jsp:attribute name="title">
			Edit User Details
	</jsp:attribute>
	<jsp:attribute name="navbar">
			<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
<jsp:body>
<a href="<s:url action="view.action"/>">Display Users</a>
<br><br>

<h3>Update Details</h3>

<jsp:include page="userform.jsp"/>

</jsp:body>

</t:wrapper>
