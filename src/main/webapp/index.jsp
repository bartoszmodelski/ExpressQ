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
                <!-- Currently doesn't work -->
                <!-- div class="vertical-center" -->
                    <img src ="../images/swiftqv2.png" alt="swiftq"/>
                    <p class="lead">Order now to beat the queue!</p>
                    <s:if test="#session.containsKey('user')">
                    </s:if>
                    <s:else>
                    	<p>
	                        <a href="<s:url action="registration"/>" class="btn btn-lg btn-primary" role="button">Register</a>
	                        <a href="<s:url action="login"/>" class="btn btn-lg btn-primary" role="button">Login</a>
	                    </p>
                    </s:else>
                <!-- /div -->
            </div>
        </div>
        <link rel="stylesheet" href="css/cover.css">
    </jsp:body>
</t:wrapper>
