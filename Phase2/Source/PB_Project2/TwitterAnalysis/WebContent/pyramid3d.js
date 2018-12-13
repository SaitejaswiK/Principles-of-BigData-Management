$(function () {

    $('#container').highcharts({
        chart: {
            type: 'pyramid',
            marginRight: 100
        },
        title: {
            text: 'Top 10 Users Tweeted on Diseases',
            x: -50
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b> ({point.y:,.0f})',
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
                    softConnector: true
                }
            }
        },
        legend: {
            enabled: false
        },
        series: [{
            name: 'Tweet Count',
            data: [
                ['Health and Fitness',1], 
                ['Minimalistmeso',17],
                ['Sarah Feagan',2],
                ['remi.winehouse',1],
                ['Francis Huynh',6],
                ['TB/HIV Activist G..',4],
                ['Grey Matters Tumo',2],
                ['AMSE',2], 
                ['Dr. Waleed Al-Salem',3],
                ['Asthama Causes',4], 
            ]
        }]
    });
});