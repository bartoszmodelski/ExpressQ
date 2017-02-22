<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
	
<t:wrapper>
    <jsp:attribute name="title">
		Registration
	</jsp:attribute>
	<jsp:attribute name="script">
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
	</jsp:attribute>	
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h3>ExpressQ Registration</h3>
		<br>
		<s:form name="registration" method = "POST" action="reg" onsubmit="return validateForm()">
			<s:textfield name= "Uname" label = "Username"/>
			<s:password name= "Pass" label = "Password"/>
			<s:password name = "PassConf" label ="Confirm Password"/>
			<s:textfield name= "Email" label = "Email Adress"/>
			<s:textfield name= "Fname" label = "First Name"/>
			<s:textfield name= "Lname" label = "Last Name"/>
			<s:submit value="Register"/>
		</s:form>
    </jsp:body>
</t:wrapper>

