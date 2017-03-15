<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Menus
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="/partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h1>Menus</h1>
		<a href="<s:url action="NewMenu.action"/>">Add a Menu</a><br><br>
		<h4>Your Menus</h4>
			<table>
				<form name="menuDisplay" method="post">
				<tr>
				<th></th>
				<th>Menu</th>
				</tr>
				<logic:iterate>
					<s:iterator value="Map">
						<tr>
						<td><input type="checkbox" value=<s:property value="value"/> name= "deleteSelection"></td>
						<td><a href="/menus?id=<s:property value="value"/>"><s:property value="key"/></a></td>
						</tr>
					</s:iterator>
				</logic:iterate>
				<input type="button" value="Delete Selected Menus" onclick="deleteRecord();">	
				</form>	
			</table>
			<script type="text/javascript">
				function deleteRecord(){
				document.menuDisplay.action="delmenu.action";
				document.menuDisplay.submit();}
			</script>
    </jsp:body>
</t:wrapper>