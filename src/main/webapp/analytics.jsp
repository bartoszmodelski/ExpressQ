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
        <script>
    		var color = Chart.helpers.color;
            var barChartData = {
                labels: [<s:property escapeHtml="false" value="labels"/>],
                datasets: [{
                    label: '<s:property escapeHtml="false" value="datasetTitle"/>',
                    backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                    borderColor: window.chartColors.red,
                    borderWidth: 1,
                    data: [<s:property escapeHtml="false" value="data"/>]
                }]

            };

            window.onload = function() {
                var ctx = document.getElementById("canvas").getContext("2d");
                window.myBar = new Chart(ctx, {
                    type: 'bar',
                    data: barChartData,
                    options: {
                        responsive: true,
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: '<s:property escapeHtml="false" value="chartTitle"/>'
                        },
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true,
                                    callback: function(value, index, values) {
                                      if(parseInt(value) >= 1000){
                                        return '£' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                                      } else {
                                        return '£' + value;
                                      }
                                    }
                                  }
                                }]
                              }
                    }
                });

            };
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <canvas id="canvas"></canvas>
        </div>
    </jsp:body>
</t:wrapper>
