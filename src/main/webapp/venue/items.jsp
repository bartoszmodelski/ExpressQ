<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Items
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp" />
	</jsp:attribute>
    <jsp:body>
		<h1>Items</h1>
		<a href="<s:url action="NewItem.action"/>">Add a new item under this section</a><br><br>
		<h4>Your Sections</h4>
		<table>
			<form name="itemDisplay" method="post">
				<tr>
				<th></th>
				<th>Item Name</th>
				<logic:iterate>
					<s:iterator value="Items">
						<tr>
						<td><input type="checkbox" value=<s:property value="value"/> name= "sectionDeleteSelection"></td>
						<td><a href="javascript:viewItems(<s:property value="value"/>)"><s:property value="key"/></a></td>
						<td><a href="javascript:editSection(<s:property value="value"/>)">Edit Item</a></td>
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
			
			function viewItems(id){
				document.sectionDisplay.action="viewItems.action?selectedSectionID="+id;
				document.sectionDisplay.submit();
			}
		</script>
    </jsp:body>
</t:wrapper>