<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

				<select = "hour">
					<option value="unspecified"></option>
					<% for(int i = 8; i < 13; i += 1) { %>
							<option value="<%=i%>"><%=i%></option>
					<% } %>
					<% for(int i = 1; i < 8; i += 1) { %>
							<option value="<%=i%>"><%=i%></option>
					<% } %>
				</select>
				:
				<select = "minute">
					<option value="unspecified"></option>
					<% for(int i = 0; i < 60; i += 5) { %>
							<option value="<%=i%>"><%=i%></option>
					<% } %>
				</select>