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
        <section id="banner">
            <div class="container-fluid">
                <div class="jumbotron text-center">
                    <h1>ExpressQ</h1>
                    <p>Generic boilerplate marketing talk goes here</p>
                    <p>
                        <a href="<s:url action="registration"/>" class="btn btn-lg btn-primary"
                           role="button">Register</a>
                    </p>
                </div>
            </div>
        </section>
        <style>html,
        body {
            background: url(/images/background.jpg) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }</style>
    </jsp:body>
</t:wrapper>

