<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
	
<t:wrapper>
    <jsp:attribute name="title">
		Business Registration
	</jsp:attribute>
	<jsp:attribute name="script">
	<script>
		function validateForm() {
			var name = document.forms["businessregistration"]["name"].value;
			var password = document.forms["businessregistration"]["pass"].value;
			var address = document.forms["businessregistration"]["address"].value;
			var phonenumber = document.forms["businessregistration"]["phonenumber"].value;
			var passwordconfirm = document.forms["businessregistration"]["PassConf"].value;
			if (name == "" || password == "" || address == "" || phonenumber == "" ||  passwordconfirm == "") {
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
		<jsp:include page="/partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h3>SwiftQ Business Registration</h3>
		<s:form name="businessregistration" method = "POST" action="busreg" onsubmit="return validateForm()">
			<s:textfield name= "name" label = "Name"/>
			<s:textfield name= "address" label = "Address"/>
			<s:textfield name= "phonenumber" label = "Phone Number"/>>
			<s:password name= "pass" label = "Password"/>
			<s:password name = "PassConf" label ="Confirm Password"/>
			<s:submit value="Register"/>
		</s:form>
    </jsp:body>
</t:wrapper>