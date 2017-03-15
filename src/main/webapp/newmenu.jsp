<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<t:wrapper>
    <jsp:attribute name="title">
		Add New Menu
	</jsp:attribute>
	    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp" />
	</jsp:attribute>
	<jsp:body>
		<h3>Add a Menu</h3>
		<s:form name="menu" method = "POST" action="InsertMenu" onsubmit="return validateForm()">
			<s:textfield name= "name" label = "Menu Name"/>
			<s:textfield name= "description" label = "Menu Description"/>
			<s:submit value="Add"/>
		</s:form>
    </jsp:body>
</t:wrapper>