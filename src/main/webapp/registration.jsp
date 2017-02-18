<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
<script>
	function validateForm() {
		var username = document.forms["registration"]["Uname"].value;
		var password = document.forms["registration"]["Pass"].value;
		var email = document.forms["registration"]["Email"].value;
		var firstname = document.forms["registration"]["Fname"].value;
		var lastname = document.forms["registration"]["Lname"].value;
		var passwordconfirm = document.forms["registration"]["PassConf"].value;

		if (username == "" || password == "" || email == "" || firstname == "" || lastname == "" || passwordconfirm == "") {
			alert("All fields must be filled out.");
			return false;
		}
		
	    if(password != passwordconfirm){
	    	alert("Passwords must match.");
	    	return false;
	    }
	}
</script>
</head>
<body>
<h3>ExpressQ Registration</h3>

	<s:form name="registration" method = "POST" action="reg" onsubmit="return validateForm()">
		<s:textfield name= "Uname" label = "Username:"/>
		<s:textfield name= "Pass" label = "Password:"/>
		<s:textfield name = "PassConf" label ="Confirm Password"/>
		<s:textfield name= "Email" label = "Email Adress:"/>
		<s:textfield name= "Fname" label = "First Name:"/>
		<s:textfield name= "Lname" label = "Last Name:"/>
		<s:submit value="Registration"/>
	</s:form>
</body>
</html>
