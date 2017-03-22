<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>
<t:wrapper>
    <jsp:attribute name="title">
        User Profile
    </jsp:attribute>
    <jsp:attribute name="navbar">
        <jsp:include page="partials/navbar.jsp"/>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <h3 class="text-center">Update Details of <s:property value="%{(#session['user']).getUsername()}"/></h3>
            <jsp:include page="userprofileform.jsp"/>
        </div>
    </jsp:body>
</t:wrapper>
