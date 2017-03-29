<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		No Items Selected
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class=container>
           <h2 class="text-center">No Items Selected</h2>
           <p class ="text-center">You must select at least 1 item to make a purchase.</p>
        </div>

    </jsp:body>
</t:wrapper>