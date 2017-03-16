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
		<h1>Fatal error #1</h1>
        <p>Application encountered fatal error. Please try again.</p>
		<p>If it persist contact webmaster: username@server.com</p>
    </jsp:body>
</t:wrapper>