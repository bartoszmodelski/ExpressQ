<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		Insert New User
	</jsp:attribute>
    <jsp:attribute name="script">
	<script>
        function validateForm() {
            var username = document.forms["insertuser"]["Uname"].value;
            var password = document.forms["insertuser"]["Pass"].value;
            var email = document.forms["insertuser"]["Email"].value;
            var firstname = document.forms["insertuser"]["Fname"].value;
            var lastname = document.forms["insertuser"]["Lname"].value;
            var passwordconfirm = document.forms["insertuser"]["PassConf"].value;

            if (username == "" || password == "" || email == "" || firstname == "" || lastname == "" || passwordconfirm == "") {
                alert("All fields must be filled out.");
                return false;
            }

            if (password != passwordconfirm) {
                alert("Passwords must match.");
                return false;
            }
        }
    </script>
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <s:form name="insertuser" method="POST" action="insertuser" onsubmit="return validateForm()"
                    cssClass="form-signin">
                <h3 class="form-signin-heading">SwiftQ Insert User</h3>
                <s:textfield name="username" label="Username" cssClass="form-signin"/>
                <s:password name="password" label="Password" cssClass="form-signin"/>
                <s:password name="passwordConf" label="Confirm Password" cssClass="form-signin"/>
                <s:textfield type = "email" name="Email" label="Email Address" cssClass="form-signin"/>
                <s:textfield name="fName" label="First Name" cssClass="form-signin"/>
                <s:textfield name="lName" label="Last Name" cssClass="form-signin"/>
                <s:select label="Type"  name="Type" list ="{0,1,2}" cssClass="form-control" />
                <s:submit value="Add User" cssClass="form-signin btn btn-lg btn-block btn-primary"
                          cssStyle="margin-top: 10px;"/>
            </s:form>
        </div>
        <link rel="stylesheet" href="css/login.css">
    </jsp:body>
</t:wrapper>

