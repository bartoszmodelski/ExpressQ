<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-static-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/" >SwiftQ</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <s:if test="%{#session['user']!=null}">
                <s:if test= "%{#session['user'].getType().equals(0)}">
                	<li><a href="<s:url action="index"/>">Home</a></li>
            		<li><a href="<s:url action="profile"/>"><s:property value="%{(#session['user']).getUsername()}"/></a></li>
            		<li><a href="<s:url action="viewQR"/>">View QR Codes</a></li>
            		<li><a href="<s:url action="venues"/>">Venues</a></li>
            		<li><a href="<s:url action="logout"/>">Logout</a></li>
                </s:if>
                <s:elseif test= "%{#session['user'].getType().equals(1)}">
                	<li><a href="<s:url action="index"/>">Home</a></li>
            		<li><a href="<s:url action="profile"/>"><s:property value="%{(#session['user']).getUsername()}"/></a></li>
            		<li><a href="<s:url action="viewQR"/>">View QR Codes</a></li>
            		<li><a href="<s:url action="view"/>">Admin Controls</a></li>
            		<li><a href="<s:url action="venues"/>">Venues</a></li>
            		<li><a href="<s:url action="logout"/>">Logout</a></li>
                </s:elseif>
                <s:elseif test= "%{#session['user'].getType().equals(2)}">
                	<li><a href="<s:url action="index"/>">Home</a></li>
            		<li><a href="<s:url action="profile"/>"><s:property value="%{(#session['user']).getUsername()}"/></a></li>
            		<li><a href="<s:url action="viewQR"/>">View QR Codes</a></li>
            		<li><a href="<s:url action="venuehome"/>">Menu Controls</a></li>
            		<li><a href="<s:url action="venues"/>">Venues</a></li>
            		<li><a href="<s:url action="logout"/>">Logout</a></li>
                </s:elseif>
        </s:if>
        <s:if test="%{#session['user']==null}">
        	    <li><a href="<s:url action="index"/>">Welcome</a></li>
        	    <li><a href="<s:url action="login"/>">Login</a></li>
        	    <li><a href="<s:url action="registration"/>">Register</a></li>
        	    <li><a href="<s:url action="venues"/>">Venues</a></li>
        </s:if>
    </ul>
  </div>
</nav>
