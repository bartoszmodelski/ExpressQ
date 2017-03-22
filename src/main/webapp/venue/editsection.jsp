<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Edit Section
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
    	<h3>Update Section Name</h3>
    	<a href="<s:url action="venuehome.action"/>">Return to sections without saving changes.</a><br><br>
		<s:form name="sectionupdate" method = "POST" action="updatesection">
			<s:hidden name ="sectionID" value = "%{selectedSectionID}"/>
			<s:textfield name = "NewName" value = "%{Name}" label = "New Section Name"/>
			<s:submit value="Update"/>
		</s:form>
    </jsp:body>
</t:wrapper>
