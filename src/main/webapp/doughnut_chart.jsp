<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
$("#<s:property escapeHtml="false" value="pId"/>").click(function() {
var color = Chart.helpers.color;
	var config = {
	        type: 'pie',
	        data: {
	            datasets: [{
	                data: [<s:property escapeHtml="false" value="data"/>],
	                backgroundColor: [
	                    window.chartColors.red,
	                    window.chartColors.orange,
	                    window.chartColors.yellow,
	                    window.chartColors.green,
	                    window.chartColors.blue,
						window.chartColors.purple,
                    	window.chartColors.grey,
	                ],
		            label: '<s:property escapeHtml="false" value="datasetTitle"/>'
	            }],
	            labels: [<s:property escapeHtml="false" value="labels"/>]
	        },
	        options: {
	            responsive: true,
	            maintainAspectRatio: false,
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
	    
	  	$('#canvas1').remove();
	  	$('#chartdiv').append('<canvas id="canvas1"><canvas>');
	    
		var ctx = document.getElementById("<s:property escapeHtml="false" value="canvasId"/>").getContext("2d");
		ctx.canvas.width = 100;
		ctx.canvas.height = 550;
	    window.myDoughnut = new Chart(ctx, config);
});
