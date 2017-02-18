<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Confirm Order</title>
</head>
<body>
	<h1>Please Confirm Your Order</h1>
	<s:actionerror />
	<s:form method = "POST" action="conf">
		<s:submit value="Confirm Order" onclick="myFunction()"/>
	</s:form>
</body>
</html>
