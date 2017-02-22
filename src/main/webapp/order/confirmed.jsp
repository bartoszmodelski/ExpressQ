<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Order Placed</title>
</head>
<body>
<script type="text/javascript" src="jquery.qrcode.js"></script>
<script type="text/javascript" src="qrcode.js"></script>
<h1>Order Placed</h1>	
<p>Please bring the QR code to the store to collect your order.</p>
<p>Last Order Number was: <s:property value="transactionID"/></p>
<span style="color:white" id="lastOrder"><s:property value="transactionID" /></span>
<div id="qrcode"></div>
<script type="text/javascript">
	var id = lastOrder.innerHTML;
	new QRCode(document.getElementById("qrcode"), id);
</script>
</body>
</html>