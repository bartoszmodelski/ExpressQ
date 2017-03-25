<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="navbar" fragment="true" %>
<%@attribute name="script" fragment="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="stylesheet" href="../../css/sticky-footer.css">
    <link rel="stylesheet" href="../../css/bootstrap-theme.css">
</head>
<body>
<jsp:invoke fragment="navbar"/>
<jsp:include page="/messages.jsp"/>
<jsp:doBody/>
<footer class="footer">
    <div class="container">
        <p class="text-muted text-center">2017 SwiftQ</p>
    </div>
</footer>
<script src="../../js/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<jsp:invoke fragment="script"/>
</body>
</html>