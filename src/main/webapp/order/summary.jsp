<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Error
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h3>Check your order</h1>
		<s:actionerror />
		<s:form method = "POST" action="confirmOrder">
			<br>
			<table>
			<col width="200">
			<col width="100">
			<col width="100">
			<col width="100">
			<tr><th>Item</th><th>Price</th><th>Quantity</th><th>Total</th></tr>
			<s:iterator value="order.itemsAndQuantities">
				<tr><td><s:property value="key.name"/></td><td><s:property value="key.price"/></td><td><s:property value="value"/></td><td>sum</td></tr>
			</s:iterator>
			<td colspan="3"></td><td>grand sum</td>
			</table>
			<br><h3>Specify time of collection</h3>
			<jsp:include page="selects.jsp"/>
			<br>
			<br><h3>Confirm your order</h3>
			<s:submit value="Confirm Order"/>
		</s:form>
    </jsp:body>
	
</t:wrapper>


