<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Venues
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <s:actionerror/>
        <div class="container">
            <h3>Available venues</h3>
            <div class="list-group">
                <logic:iterate>
                    <s:iterator value="Map"><a href="/venues?id=<s:property value="value"/>"
                                               class="list-group-item"><s:property
                            value="key"/></a>
                    </s:iterator>
                </logic:iterate>
            </div>
        </div>
    </jsp:body>
</t:wrapper>
