<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>
<html>
<head>
	<script>
		function validateForm() {
			var password = document.forms["UserUpdate"]["User.Password"].value;
			var passwordconfirm = document.forms["UserUpdate"]["PasswordConf"].value;
			
			
			if(password != passwordconfirm){
				alert("Passwords must match.");
				return false;
			}
		}
	</script>
</head>
<body>
	<h3>Update Details of <s:property value="%{#session['loginId']}"/></h3>
	<s:form action="UserUpdate" onsubmit="return validateForm()">
		<s:textfield label="Username" value="%{#application.Username}" name="User.Username" readonly="true"/>
		<s:textfield label="Password" value="%{#application.Password}" name="User.Password" type="Password"/>
		<s:textfield label="Confirm Password" value="hello" name="PasswordConf" type="Password"/>
		<s:textfield label="Forename" value="%{#application.Fname}" name="User.Fname" />
		<s:textfield label="Surname" value="%{#application.Lname}" name="User.Lname" />
		<s:textfield label="Email" value="%{#application.email}" name="User.email" />
		<s:submit value="Update" />
	</s:form>
</body>
</html>
