<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
	
<t:wrapper>
    <jsp:attribute name="title">
		Home
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		Hello <s:property value="%{#session['loginId']}"/>
		<br><a href="<s:url action="logout"/>">LogOut</a>
    </jsp:body>
</t:wrapper>

