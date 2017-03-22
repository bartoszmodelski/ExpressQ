<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>

<s:form action="UserUpdate" onsubmit="return validateForm()">
		<s:textfield label="Username" value="%{#application.Username}" name="User.Username" readonly="true"/>
		<s:textfield label="Password" value="" name="User.Password" type="Password"/>
		<s:textfield label="Confirm Password" value="" name="PasswordConf" type="Password"/>
		<s:textfield label="Forename" value="%{#application.Fname}" name="User.Fname" />
		<s:textfield label="Surname" value="%{#application.Lname}" name="User.Lname" />
		<s:textfield label="Email" value="%{#application.email}" name="User.email" />
		<s:submit value="Update" />
</s:form>

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