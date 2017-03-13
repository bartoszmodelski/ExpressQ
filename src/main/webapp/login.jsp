<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<t:wrapper>
    <jsp:attribute name="title">
		Login
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class=container>
            <s:actionmessage/>
            <s:actionerror/>
            <br>
            <s:form id="idLoginForm" action="loginUser" cssClass="form-signin">
                <h2 class="form-signin-heading">Login below</h2>
                <label for="userName" class="sr-only">Username</label>
                <s:textfield name="userName" placeholder="Username" cssClass="form-control"/>
                <label for="password" class="sr-only">Password</label>
                <s:password name="password" placeholder="Password" cssClass="form-control"/>
                <s:submit type="submit" cssClass="btn btn-lg btn-primary btn-block" value="Submit"/>
            </s:form>
        </div>
        <link rel="stylesheet" href="css/login.css">
    </jsp:body>
</t:wrapper>

