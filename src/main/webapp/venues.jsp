
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Venues</title>
<body>
	<!-- action<> for testing purposes, delete when later -->
  <h1>Struts <logic:iterate> Venues</h1>
  <s:property value="Table" />
</body>
</html>
