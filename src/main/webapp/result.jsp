<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<body>
		<h1>Login Success</h1>
		<h4>Hello <s:property value="#session.loginId" /></h4>
		 <s:url id="url" action="HelloWorld">
		 </s:url>
		        	<s:a href="%{url}">Click me</s:a>
		
	</body>
</html>