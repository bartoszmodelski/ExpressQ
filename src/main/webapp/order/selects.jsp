<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

				<select name = "hour">
					<option value="unspecified"></option>
					<% for(int i = 8; i < 13; i += 1) { %>
							<option value="<%=i%>"><%=i%> AM</option>
					<% } %>
					<% for(int i = 1; i < 8; i += 1) { %>
							<option value="<%=i + 12%>"><%=i%> PM</option>
					<% } %>
				</select>
				:
				<select name = "minute">
					<option value="unspecified"></option>
					<% for(int i = 0; i < 60; i += 5) { %>
							<option value="<%=i%>"><%=i%></option>
					<% } %>
				</select>
