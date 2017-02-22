<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% if (!(((String)request.getAttribute("keywords")).equals(""))) { %>
Alternatively you can also pick up the order by mentioning your name and following magic words at the till: <strong><s:property value="keywords" /></strong>.
<% } %>