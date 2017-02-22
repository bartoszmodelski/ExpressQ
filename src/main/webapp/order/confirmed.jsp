<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
	
<t:wrapper>
    <jsp:attribute name="title">
		Order placed
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<script type="text/javascript" src="jquery.qrcode.js"></script>
		<script type="text/javascript" src="qrcode.js"></script>
		<h1>Order Placed</h1>	
		<p>Please bring the QR code to the store to collect your order.</p>
		<p>Order number: <s:property value="transactionID"/></p>
		<span style="color:white" id="lastOrder"><s:property value="transactionID" /></span>
		<div id="qrcode"></div>
		<script type="text/javascript">
			var id = lastOrder.innerHTML;
			new QRCode(document.getElementById("qrcode"), id);
		</script>
    </jsp:body>
</t:wrapper>