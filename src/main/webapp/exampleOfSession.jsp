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
		<s:if test="#session.containsKey('loginId')">
			Hello <s:property value="%{#session['loginId']}"/>. <br><a href="<s:url action="logout"/>">LogOut</a>
		</s:if>
		<s:elseif test="#session.containsKey('businessId')">
					Hello <s:property value="%{#session['businessId']}"/>. <br><a href="<s:url action="businessLogout"/>">LogOut</a>
		
		</s:elseif>
		<s:else>
			You ain't logged in m8. 
		</s:else>
    </jsp:body>
</t:wrapper>

