<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>
<t:wrapper>
	<jsp:attribute name="title">
			Edit User Details
	</jsp:attribute>
    <jsp:attribute name="navbar">
			<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <a href="<s:url action="view.action"/>">Display Users</a>
            <br><br>

            <h3 class="text-center">Update Details</h3>

            <jsp:include page="userform.jsp"/>
        </div>
    </jsp:body>

</t:wrapper>
