<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">ExpressQ</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="<s:url action="login"/>">Login</a></li>
      <li><a href="<s:url action="registration"/>">Register</a></li>
      <li><a href="<s:url action="venues"/>">Venues</a></li>
    </ul>
  </div>
</nav>