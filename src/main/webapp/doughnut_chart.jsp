<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
$("#<s:property escapeHtml="false" value="pId"/>").click(function() {
	var config = {
	        type: 'doughnut',
	        data: {
	            datasets: [{
	                data: [<s:property escapeHtml="false" value="data"/>],
	                backgroundColor: [
	                    window.chartColors.red,
	                    window.chartColors.orange,
	                    window.chartColors.yellow,
	                    window.chartColors.green,
	                    window.chartColors.blue,
	                ],
		            label: '<s:property escapeHtml="false" value="datasetTitle"/>'
	            }],
	            labels: [<s:property escapeHtml="false" value="labels"/>]
	        },
	        options: {
	            responsive: true,
	            legend: {
	                position: 'top',
	            },
	            title: {
	                display: true,
	                text: '<s:property escapeHtml="false" value="chartTitle"/>'
	            },
	            animation: {
	                animateScale: true,
	                animateRotate: true
	            }
			}
	    };
		var ctx = document.getElementById("<s:property escapeHtml="false" value="canvasId"/>").getContext("2d");
	    window.myDoughnut = new Chart(ctx, config);
});
