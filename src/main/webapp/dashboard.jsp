<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
        Analytics
	</jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="css/analytics.css">
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
                <script src="/chart?type=TransactionsPerHour&fromDate=2016-12-10&toDate=2018-01-01&canvasId=TransactionsPerHour&pId=TransactionsPerHourBtn"></script>
        </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col-sm-2">
                    <nav class="nav flex-column">
                        <p id="ACSperMonthBtn">Average Customer Spending per Month</p>
                        <p id="ACSperWeekBtn">Average Customer Spending per Week</p>
                        <p id="AllItemsPopularityBtn">Item Popularity</p>
                        <p id="TransactionsPerHourBtn">Transactions per Hour</p>
                        <p id="itemSaleBtn">Sales of Item (id=32)</p>
                        <p id="mostPopularPairsBtn">Common item pairings</p>
                    </nav>
                </div>
                <div class="container" style="margin-left: 20px;">
                    <div class="col-sm-10">
                        <br><br>
                        <canvas id="ACSperMonth"></canvas>
                        <br><br>
                        <canvas id="ACSperWeek"></canvas>
                        <br><br>
                        <canvas id="AllItemsPopularity"></canvas>
                        <br><br>
                        <canvas id="TransactionsPerHour"></canvas>
                        <br><br>
                        <canvas id="itemSale"></canvas>
                        <br><br>
                        <canvas id="mostPopularPairs"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>

</t:wrapper>
