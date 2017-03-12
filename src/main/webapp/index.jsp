<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<t:wrapper>
    <jsp:attribute name="title">
		Welcome
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="row text-center">
                <h1>ExpressQ</h1>
                <p class="lead">Generic boilerplate marketing talk goes here</p>
                <p>
                    <a href="<s:url action="registration"/>" class="btn btn-lg btn-primary"
                       role="button">Register</a>
                </p>
            </div>
        </div>
        <link rel="stylesheet" href="css/cover.css">
    </jsp:body>
</t:wrapper>
