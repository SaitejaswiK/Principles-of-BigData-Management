google.charts.load('current', {'packages':['geochart']});
google.charts.setOnLoadCallback(drawRegionsMap);

function drawRegionsMap() {

    var data = google.visualization.arrayToDataTable([
        ['Country', 'Count'],
        ['US', 32],
        ['GB', 0],
        ['FR', 25],
        ['CA', 3],
        ['ES', 0],
        ['AU', 3],
        ['ID', 0],
        ['MX', 0],
        ['CM', 0],
        ['AR', 0],
        ['ZA', 0],
        ['NG', 0],
        ['CO', 0],
        ['IN', 40],
        ['MY', 0],
        ['BR', 0],
        ['PH', 3],
        ['AT', 0],
        ['VE', 14],
        ['NL', 3],
    ]);

    var options = {};

    var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));

    chart.draw(data, options);
}
