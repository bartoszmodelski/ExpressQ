<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
        Analytics
	</jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="../../css/analytics.css">
    </jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
        <jsp:attribute name="script">
                <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.bundle.min.js"></script>
                <script src="../../js/utils.js"></script>
                <script src="/chart?type=ACSperMonth"></script>
        </jsp:attribute>
    <jsp:body>
        <div class="container">
    		<br>Available charts:
    		<br>- type=ACSperMonth
    		<br>- type=ACSperWeek
            <p>CLICK!</p>
            <br><br><canvas style="-moz-user-select:none; -webkit-user-select:none; -ms-user-select:none;" id="canvas"></canvas>
        </div>
    </jsp:body>

</t:wrapper>
