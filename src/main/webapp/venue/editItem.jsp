<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<t:wrapper>
    <jsp:attribute name="title">
		Edit Item
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
         <div class="container">
	    	<h3 class="text-center">Update Item Details</h3>
			<s:form cssStyle="max-width: 330px; padding: 15px; margin: 0 auto;" name="itemupdate" method = "POST" action="updateitem">
				<s:hidden name ="itemID" value = "%{itemDetails.ItemID}" cssClass="form-control"/>
				<s:hidden name ="sectionID" value = "%{itemDetails.SectionID}" cssClass="form-control"/>
				<s:textfield name = "Name" value = "%{itemDetails.Name}" label = "Name" cssClass="form-control" required="true"/>
				<s:textfield name = "Description" value = "%{itemDetails.Description}" label = "Description" cssClass="form-control"/>
				<s:textfield type = "number" step = "0.01" name = "Price" value = "%{itemDetails.Price}" label = "Price" cssClass="form-control" min="0.01" required="true"/>
				<s:textfield min = "1" type= "number" name = "Stock" value = "%{itemDetails.Stock}" label = "Stock" cssClass="form-control" required="true"/>
				<s:textfield name = "Allergens" value = "%{itemDetails.Allergens}" label = "Allergens" cssClass="form-control"/>
				<s:textfield min = "0" type ="number" name = "preparationtime" value = "%{itemDetails.PreparationTime}" label = "PreparationTime" cssClass="form-control" required="true"/>
				<s:submit value="Update" cssStyle="float: right;" cssClass="btn btn btn-primary btn-sm"/>
			</s:form>
		</div>
    </jsp:body>
</t:wrapper>
