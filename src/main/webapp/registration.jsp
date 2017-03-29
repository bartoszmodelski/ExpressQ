<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

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

            if (password != passwordconfirm) {
                alert("Passwords must match.");
                return false;
            }
        }
    </script>
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <s:form name="registration" method="POST" action="reg" onsubmit="return validateForm()"
                    cssClass="form-signin">
                <h3 class="form-signin-heading">ExpressQ Registration</h3>
                <s:textfield name="Uname" placeholder="Username" cssClass="form-signin"/>
                <s:password name="Pass" placeholder="Password" cssClass="form-signin"/>
                <s:password name="PassConf" placeholder="Confirm Password" cssClass="form-signin"/>
                <s:textfield type = "email" name="Email" placeholder="Email Address" cssClass="form-signin"/>
                <s:textfield name="Fname" placeholder="First Name" cssClass="form-signin"/>
                <s:textfield name="Lname" placeholder="Last Name" cssClass="form-signin"/>
                <s:submit value="Register" cssClass="form-signin btn btn-lg btn-block btn-primary"
                          cssStyle="margin-top: 10px;"/>
            </s:form>
        </div>
        <link rel="stylesheet" href="css/login.css">
    </jsp:body>
</t:wrapper>

