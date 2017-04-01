<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		Login
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <br>
            <s:form id="idLoginForm" action="loginUser" cssClass="form-signin">
                <h2 class="form-signin-heading text-center">Login below</h2>
                <label for="userName" class="sr-only">Username</label>
                <s:textfield name="userName" placeholder="Username" cssClass="form-control"/>
                <label for="password" class="sr-only">Password</label>
                <s:password name="password" placeholder="Password" cssClass="form-control"/>
                <s:submit type="submit" cssClass="btn btn-lg btn-primary btn-block" cssStyle="margin-top: 10px;"
                          value="Submit"/>
            </s:form>
        </div>
        <link rel="stylesheet" href="css/login.css">
    </jsp:body>
</t:wrapper>
