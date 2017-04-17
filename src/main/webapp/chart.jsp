<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
$("p").click(function() {
    alert("werkz");
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
    
});