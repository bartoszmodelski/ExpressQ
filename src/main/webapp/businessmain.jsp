<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>
<html>
<head>
	<script type="text/javascript">
	function deleteRecord(){
		document.userDisplay.action="del.action";
		document.userDisplay.submit();
	}
	function editUser(val){
		document.userDisplay.action="edit.action?selectedID="+val;
		document.userDisplay.submit();
	}
	</script>
</head>
<body>
	<a href="<s:url action="NewMenu.action"/>">Add a Menu</a>
	<br><br>
	<table>
		<form name="userDisplay" method="post">
					<tr>
						<th></th>
						<th>Username</th>
						<th>Forename</th>
						<th>Surname</th>
						<th>Email</th>
						<th>Admin Permission</th>
					</tr>
			<%
			List userList = (List)request.getAttribute("disp");
			if(userList != null){	 
				Iterator it = userList.iterator();	 
				while(it.hasNext()){		 
					com.delta.expressq.util.User user = (com.delta.expressq.util.User)it.next();
					int UserID = user.getUserID();
					String UserName = user.getUsername();
					String Fname = user.getFname();
					String Lname = user.getLname();
					String email = user.getemail();
					int Admin = user.getAdmin();
					%>
					<tr>
						<td><input type="checkbox" value="<%= UserID %>" name= "deleteSelection"></td>
						<td><%= UserName %></td>
						<td><%= Fname %></td>
						<td><%= Lname %></td>
						<td><%= email %></td>
						<td><%= Admin %></td>
						<td><a href="javascript:editUser('<%= UserID %>')">Edit User</a></td>
					</tr>
					<%
				}
			} 
			%>
			<input type="button" value="Delete Selected Users" onclick="deleteRecord();">		 
		</form>
	</table>
</body> 
</html>