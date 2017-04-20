<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
$("#<s:property escapeHtml="false" value="pId"/>").click(function() {
	var color = Chart.helpers.color;
    var barChartData = {
        labels: [<s:property escapeHtml="false" value="labels"/>],
            datasets: [{
                label: '<s:property escapeHtml="false" value="datasetTitle"/>',
                backgroundColor: color( <s:property escapeHtml="false" value="colour"/>).alpha(0.5).rgbString(),
                borderColor: <s:property escapeHtml="false" value="colour"/>,
                borderWidth: 1,
                data: [<s:property escapeHtml="false" value="data"/>]
            }]
        };

	  $('#canvas1').remove();
	  $('#chartdiv').append('<canvas id="canvas1"><canvas>');

        var ctx = document.getElementById("<s:property escapeHtml="false" value="canvasId"/>").getContext("2d");
        myBar = new Chart(ctx, {
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
                                    return '<s:property escapeHtml="false" value="currency"/>' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '<s:property escapeHtml="false" value="symbol"/>';
                                } else {
                                    return '<s:property escapeHtml="false" value="currency"/>' + value + '<s:property escapeHtml="false" value="symbol"/>';
                                }
                            }
                        }
                    }]
                }
            }
        });
});
