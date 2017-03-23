<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Edit Item
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
    	<h3>Update Item Details</h3>
		<s:form name="itemupdate" method = "POST" action="updateitem">
			<s:hidden name ="itemID" value = "%{itemDetails.ItemID}"/>
			<s:hidden name ="sectionID" value = "%{itemDetails.SectionID}"/>
			<s:textfield name = "Name" value = "%{itemDetails.Name}" label = "Name"/>
			<s:textfield name = "Description" value = "%{itemDetails.Description}" label = "Description"/>
			<s:textfield name = "Price" value = "%{itemDetails.Price}" label = "Price"/>
			<s:textfield name = "Stock" value = "%{itemDetails.Stock}" label = "Stock"/>
			<s:textfield name = "Allergens" value = "%{itemDetails.Allergens}" label = "Allergens"/>
			<s:textfield name = "PreparationTime" value = "%{itemDetails.PreparationTime}" label = "PreparationTime"/>
			<s:submit value="Update"/>
		</s:form>
    </jsp:body>
</t:wrapper>
