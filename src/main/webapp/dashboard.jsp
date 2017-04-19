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
                <!--script src="/chart?type=ItemSale"></script-->
                <script src="/chart?type=MostCommonlyTogether&fromDate=2016-12-10&toDate=2018-01-01&canvasId=mostPopularPairs&pId=mostPopularPairsBtn"></script>
                <script src="/chart?type=ItemSale&fromDate=2017-02-10&itemID=32&toDate=2017-04-20&canvasId=itemSale&pId=itemSaleBtn"></script>
                <script src="/chart?type=ACSperMonth&year=2017&canvasId=ACSperMonth&pId=ACSperMonthBtn"></script>
                <script src="/chart?type=ACSperWeek&year=2017&canvasId=ACSperWeek&pId=ACSperWeekBtn"></script>
                <script src="/chart?type=AllItemsPopularity&fromDate=2016-12-10&toDate=2018-01-01&canvasId=AllItemsPopularity&pId=AllItemsPopularityBtn"></script>
        </jsp:attribute>
    <jsp:body>
        <div class="container">
    		<br>Available charts:
    		<br>- type=ACSperMonth
    		<br>- type=ACSperWeek
            <p id="itemSaleBtn">Show item's (id=32) sale</p>
            <br><br><canvas style="-moz-user-select:none; -webkit-user-select:none; -ms-user-select:none;" id="itemSale"></canvas>
            <p id="ACSperMonthBtn">Show average customer spending per month</p>
            <br><br><canvas style="-moz-user-select:none; -webkit-user-select:none; -ms-user-select:none;" id="ACSperMonth"></canvas>
            <p id="ACSperWeekBtn">Show average customer spending per week</p>
            <br><br><canvas style="-moz-user-select:none; -webkit-user-select:none; -ms-user-select:none;" id="ACSperWeek"></canvas>
            <p id="AllItemsPopularityBtn">All items popularity</p>
            <br><br><canvas style="-moz-user-select:none; -webkit-user-select:none; -ms-user-select:none;" id="AllItemsPopularity"></canvas>
            <p id="mostPopularPairsBtn">Show items most commonly bought together</p>
            <br><br><canvas style="-moz-user-select:none; -webkit-user-select:none; -ms-user-select:none;" id="mostPopularPairs"></canvas>
        </div>
    </jsp:body>

</t:wrapper>
