<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<t:wrapper>
    <jsp:attribute name="title">
		Error
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h1>Denied</h1>
        <p>You do not have the rights to access this page.</p>
    </jsp:body>
</t:wrapper>