
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Signup</title>
<body>
<h3>ExpressQ Registration</h3>

	<s:form method = "POST" action="reg">
		<s:textfield name= "Uname" label = "Username:"/>
		<s:textfield name= "Pass" label = "Password:"/>
		<s:textfield name= "Email" label = "Email Adress:"/>
		<s:textfield name= "Fname" label = "First Name:"/>
		<s:textfield name= "Lname" label = "Last Name:"/>
		<s:submit value="Registration"/>
	</s:form>
</body>
</html>
