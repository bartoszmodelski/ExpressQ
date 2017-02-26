<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<t:wrapper>
    <jsp:attribute name="title">
		Welcome
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <link rel="stylesheet" href="css/cover.css" type="text/css">
            <div class="inner cover">
                <h1 class="cover-heading">ExpressQ</h1>
                <p class="lead">Generic boilerplate marketing talk goes here</p>
                <p class="lead">
                    <a href="<s:url action="registration"/>" class="btn btn-lg btn-default">Register</a>
                </p>
            </div>
        </div>
    </jsp:body>
</t:wrapper>

