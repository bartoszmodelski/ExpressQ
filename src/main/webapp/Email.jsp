<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Email Form</title>
</head>
<body>
	<s:form action="emailer" method="post">
		<s:textfield name= "to" label = "to"/>
		<s:textfield name= "subject" label = "subject"/>
		<s:textfield name= "body" label = "body"/>
		<s:submit value="Send"/>
	</s:form>
</body>
</html>
