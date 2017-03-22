<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<t:wrapper>
    <jsp:attribute name="title">
		Add New Item
	</jsp:attribute>
	    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
	<jsp:body>
		<h3>Add an Item</h3>
		<s:form name="item" method = "POST" action="InsertItem">
			<s:hidden name ="sectionID" value = "%{sectionID}"/>
			<s:textfield name = "itemname" label = "Item Name"/>
			<s:textfield name = "itemdescription" label = "Item Description"/>
			<s:textfield name = "price" label ="Price"/>
			<s:textfield name = "stock" label = "Stock"/>
			<s:textfield name = "allergens" label ="Allergens"/>
			<s:textfield name = "preparationtime" label ="Preparation Time"/>
			<s:submit value="Add Item"/>
		</s:form>
    </jsp:body>
</t:wrapper>