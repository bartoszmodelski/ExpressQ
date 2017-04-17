<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<t:wrapper>
    <jsp:attribute name="title">
		Edit Section
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <h3 class="text-center">Update Section Name</h3>
            <a href="<s:url action="venuehome.action"/>">Return to sections without saving changes.</a><br><br>
            <s:form cssStyle="max-width: 330px; padding: 15px; margin: 0 auto;" name="sectionupdate" method="POST"
                    action="updatesection">
                <s:hidden name="sectionID" value="%{selectedSectionID}" cssClass="form-control"/>
                <s:textfield name="NewName" value="%{Name}" label="New Section Name" cssClass="form-control" required="true"/>
                <s:submit value="Update" cssClass="btn btn btn-primary btn-sm" cssStyle="float: right;"/>
            </s:form>
        </div>
    </jsp:body>
</t:wrapper>
