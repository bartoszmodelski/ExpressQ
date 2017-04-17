<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		Add New Section
	</jsp:attribute>
	    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
	<jsp:body>
		<div class=container>
			<h3 class="text-center">Add a Section</h3>
			<s:form cssStyle="max-width: 330px; padding: 15px; margin: 0 auto;" name="menu" method = "POST" action="InsertSection">
				<s:textfield name= "description" label = "Section Name" cssClass="form-control" required="true"/>
				<s:submit value="Add Section" cssClass="btn btn-primary btn-sm"/>
			</s:form>
		</div>
    </jsp:body>
</t:wrapper>