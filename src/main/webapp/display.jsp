<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>
 
<head>
<script type="text/javascript">
function deleteRecord()
{
document.fom.action="del.action";
document.fom.submit();
}
function editr(val)
{
document.fom.action="edit.action?selectedID="+val;
document.fom.submit();
}
</script>
</head>
 
<a href="<s:url action="insert.action"/>">Insert</a>
 
<br><br>
 
<table>
<form name="fom" method="post">
<%
List l=(List)request.getAttribute("disp");
if(l!=null)
{
 
Iterator it=l.iterator();
 
while(it.hasNext())
{
 
com.delta.expressq.util.User b=(com.delta.expressq.util.User)it.next();
int UserID = b.getUserID();
String UserName = b.getUsername();
String Fname = b.getFname();
String Lname = b.getLname();
String email = b.getemail();

%>

<tr>
<td><input type="checkbox" value="<%= UserID %>" name= "deleteSelection"></td>
<td><%= UserName %></td>
<td><%= Fname %></td>
<td><%= Lname %></td>
<td><%= email %></td>
<td><a href="javascript:editr('<%= UserID %>')">Edit</a></td>
</tr>
 
<%
 
}
}
 
%>
<input type="button" value="delete" onclick="deleteRecord();">
</table>
 
</form>