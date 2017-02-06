
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<body>
	<s:actionmessage />

	<s:actionerror />

	<s:form id="idLoginForm" action="loginUser" namespace="/LoginUser">
		<s:textfield name="userName" placeholder="Username" label="Username" />
		<s:password name="password" placeholder="Password" label="Password" />
		<s:submit value="Submit" />
	</s:form>
</body>
</html>
