<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
$("#<s:property escapeHtml="false" value="pId"/>").click(function() {
		var color = Chart.helpers.color;
        var chartData = {
            labels: [<s:property escapeHtml="false" value="labels"/>],
            datasets: [{
                type: 'line',
                label: '<s:property escapeHtml="false" value="datasetTitle"/>',
                borderColor: <s:property escapeHtml="false" value="colour"/>,
                borderWidth: 2,
                fill: false,
                data: [<s:property escapeHtml="false" value="data"/>]
            }]

        };

            var ctx = document.getElementById("<s:property escapeHtml="false" value="canvasId"/>").getContext("2d");
            window.myMixedChart = new Chart(ctx, {
                type: 'bar',
                data: chartData,
                options: {
                    responsive: true,
                    title: {
                        display: true,
	                    text: '<s:property escapeHtml="false" value="chartTitle"/>'
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: true
                    }
                }
            });
});
