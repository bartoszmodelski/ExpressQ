<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		Add New Item
	</jsp:attribute>
	    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
	<jsp:body>
	<div class ="container">
		<h3 class="text-center">Add an Item</h3>
		<s:form cssStyle="max-width: 330px; padding: 15px; margin: 0 auto;"  name="item" method = "POST" action="InsertItem">
			<s:hidden name ="sectionID" value = "%{sectionID}" cssClass="form-control"/>
			<s:textfield name = "itemname" label = "Item Name" cssClass="form-control"/>
			<s:textfield name = "itemdescription" label = "Item Description" cssClass="form-control"/>
			<s:textfield name = "price" label ="Price" cssClass="form-control"/>
			<s:textfield type = "number" name = "stock" label = "Stock" cssClass="form-control"/>
			<s:textfield name = "allergens" label ="Allergens" cssClass="form-control"/>
			<s:textfield type = "number" name = "preparationtime" label ="Preparation Time" cssClass="form-control"/>
			<s:submit value="Add Item" cssClass="btn btn-primary btn-sm"/>
		</s:form>
		</div>
    </jsp:body>
</t:wrapper>