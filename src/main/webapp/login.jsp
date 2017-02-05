<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<body>
		<h1>Login</h1>
		<s:form action="result">
			<s:textfield name="username" label="User Name"/>
			<s:password name="password" label="Password"/>
			<s:submit/>
		</s:form>
	</body>
</html>
 