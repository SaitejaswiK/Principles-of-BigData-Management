$(document).ready(function () {
    var bubbleChart = new d3.svg.BubbleChart({
        supportResponsive: true,
        //container: => use @default
        size: 600,
        //viewBoxSize: => use @default
        innerRadius: 600 / 3.5,
        //outerRadius: => use @default
        radiusMin: 50,
        //radiusMax: use @default
        //intersectDelta: use @default
        //intersectInc: use @default
        //circleColor: use @default
        data: {
            items: [
                {text: "Sassou", count: "6456"},
                {text: "Luka", count: "4518"},
                {text: "Karina Hernandz", count: "2466"},
                {text: "Hilma Westin", count: "2049"},
                {text: "Nidhi Maurya", count: "1721"},
                {text: "Jonas Brothers", count: "1720"},
                {text: "Awans", count: "1528"},
                {text: "Sherine", count: "1228"},
                {text: "Simon Naylor", count: "1153"},
            ],
            eval: function (item) {return item.count;},
            classed: function (item) {return item.text.split(" ").join("");}
        },
        plugins: [
            {
                name: "central-click",
                options: {
                    text: "",
                    style: {
                        "font-size": "12px",
                        "font-style": "italic",
                        "font-family": "Cambria",
                        //"font-weight": "700",
                        "text-anchor": "middle",
                        "fill": "black"
                    },
                    attr: {dy: "65px"},
                    centralClick: function() {
                        alert("Here is more details!!");
                    }
                }
            },
            {
                name: "lines",
                options: {
                    format: [
                        {// Line #0
                            textField: "count",
                            classed: {count: true},
                            style: {
                                "font-size": "28px",
                                "font-family": "Cambria",
                                "text-anchor": "middle",
                                fill: "black"
                            },
                            attr: {
                                dy: "0px",
                                x: function (d) {return d.cx;},
                                y: function (d) {return d.cy;}
                            }
                        },
                        {// Line #1
                            textField: "text",
                            classed: {text: true},
                            style: {
                                "font-size": "14px",
                                "font-family": "Cambria",
                                "text-anchor": "middle",
                                fill: "black"
                            },
                            attr: {
                                dy: "20px",
                                x: function (d) {return d.cx;},
                                y: function (d) {return d.cy;}
                            }
                        }
                    ],
                    centralFormat: [
                        {// Line #0
                            style: {"font-size": "50px"},
                            attr: {}
                        },
                        {// Line #1
                            style: {"font-size": "30px"},
                            attr: {dy: "40px"}
                        }
                    ]
                }
            }]
    });
});