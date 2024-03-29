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
	    <div class="container">
	    	<s:hidden name="sectionID" value="%{selectedSectionID}" />
			<h1>Items</h1>
			<a href="<s:url action="NewItem.action?sectionID=%{selectedSectionID}"/>">Add a new item under this section</a><br><br>
			<h4 class="text-center">Your Items</h4>
			<table class="table">
				<form name="itemDisplay" method="post">
					<tr>
					<th></th>
					<th>Item Name</th>
					<th></th>
					<logic:iterate>
						<s:iterator value="Items">
							<tr>
							<td><input type="checkbox" value=<s:property value="value"/> name= "itemDeleteSelection"></td>
							<td><s:property value="key"/></td>
							<td><a href="javascript:editItem(<s:property value="value"/>)">Edit Item</a></td>
							<tr>
						</s:iterator>
					</logic:iterate>
				</form>
			</table>
			<input type="button" class="btn btn-primary btn-sm" style="float: right" value="Delete Selected Items" onclick="deleteRecord();"/>
			<script type="text/javascript">
			
				function deleteRecord(){
					document.itemDisplay.action="delitems.action";
					document.itemDisplay.submit();
				}
				
				function editItem(id){
					document.itemDisplay.action="edititem.action?selectedItemID="+id;
					document.itemDisplay.submit();
				}
			</script>
		</div>
    </jsp:body>
</t:wrapper>