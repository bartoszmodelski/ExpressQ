<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="navbar" fragment="true" %>
<%@attribute name="script" fragment="true" %>
<html lang="en">
  <head>
	<title><jsp:invoke fragment="title"/></title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/sticky-footer.css">
	<style>
		.label {
			color: #000000;
			display: block;
			width: 150px;
			float: left;
			font-size: 100%;
		}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<jsp:invoke fragment="script"/>

  </head>
  <body>
	<jsp:invoke fragment="navbar"/>
      <div class="container">
      <jsp:include page="/messages.jsp"/>
        <jsp:doBody/>
      </div>

    <footer class="footer">
      <div class="container">
        <p class="text-muted">© 2017 ExpressQ</p>
      </div>
    </footer>
  </body>
</html>
