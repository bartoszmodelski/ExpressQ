 <%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>
 
       <div class="container">
            <s:actionmessage/>
            <a href="<s:url action="insertuserdisplay"/>">Insert New User</a>
            <br><br>
            <table class="table">
                <form name="userDisplay" method="post">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Username</th>
                        <th>Forename</th>
                        <th>Surname</th>
                        <th>Email</th>
                        <th>Type</th>
                    </tr>
                    </thead>
                    <%
                        List userList = (List) request.getAttribute("disp");
                        if (userList != null) {
                            Iterator it = userList.iterator();
                            while (it.hasNext()) {
                                com.delta.expressq.util.User user = (com.delta.expressq.util.User) it.next();
                                int UserID = user.getUserID();
                                String UserName = user.getUsername();
                                String Fname = user.getFname();
                                String Lname = user.getLname();
                                String email = user.getemail();
                                int Type = user.getType();
                    %>
                    <tbody>
                    <tr>
                        <td><input type="checkbox" value="<%= UserID %>" name="deleteSelection"></td>
                        <td><%= UserName %>
                        </td>
                        <td><%= Fname %>
                        </td>
                        <td><%= Lname %>
                        </td>
                        <td><%= email %>
                        </td>
                        <td><%= Type %>
                        </td>
                        <td><a href="javascript:editUser('<%= UserID %>')">Edit User</a></td>
                    </tr>
                    </tbody>
                    <%
                            }
                        }
                    %>
                </form>
            </table>
        </div>