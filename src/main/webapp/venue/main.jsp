<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Venue Home
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h1>Sections</h1>
		<a href="<s:url action="NewSection.action"/>">Add a new Section</a><br><br>
		<h4>Your Sections</h4>
		<table>
			<form name="sectionDisplay" method="post">
				<tr>
				<th></th>
				<th>Section Name</th>
				<logic:iterate>
					<s:iterator value="Map">
						<tr>
						<td><input type="checkbox" value=<s:property value="value"/> name= "sectionDeleteSelection"></td>
						<td><a href="/sections?id=<s:property value="value"/>"><s:property value="key"/></a></td>
						<td><a href="javascript:editSection(<s:property value="value"/>)">Change Section Name</a></td>
						<tr>
					</s:iterator>
				</logic:iterate>
				<input type="button" value="Delete Selected Sections" onclick="deleteRecord();">
			</form>
		</table>
		<script type="text/javascript">
			function deleteRecord(){
				document.sectionDisplay.action="delsection.action";
				document.sectionDisplay.submit();
			}
			
			function editSection(id){
				document.sectionDisplay.action="editsection.action?selectedSectionID="+id;
				document.sectionDisplay.submit();
			}
			
			
		</script>
    </jsp:body>
</t:wrapper>