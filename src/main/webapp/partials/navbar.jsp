<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Navigation -->
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/">ExpressQ</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="<s:url action="index"/>">Welcome</a></li>
      <li><a href="<s:url action="homeAction"/>">Home</a></li>
      <li><a href="<s:url action="login"/>">Login</a></li>
      <li><a href="<s:url action="registration"/>">Register</a></li>
      <li><a href="<s:url action="venues"/>">Venues</a></li>
    </ul>
  </div><!-- container-fluid -->
</nav>