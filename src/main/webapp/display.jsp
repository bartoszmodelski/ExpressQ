<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>
<t:wrapper>
    <jsp:attribute name="title">
        Admin Control
    </jsp:attribute>
    <jsp:attribute name="script">
    <script>
        function deleteRecord() {
            document.userDisplay.action = "del.action";
            document.userDisplay.submit();
        }
        function editUser(val) {
            document.userDisplay.action = "edit.action?selectedID=" + val;
            document.userDisplay.submit();
        }
    </script>
    </jsp:attribute>
    <jsp:attribute name="navbar">
        <jsp:include page="partials/navbar.jsp"/>
    </jsp:attribute>
    <jsp:body>
      <jsp:include page="controls.jsp"/>
    </jsp:body>
</t:wrapper>