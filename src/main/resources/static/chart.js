$(document).ready(function () {
    (function (H) {
        H.seriesTypes.pie.prototype.animate = function (init) {
            const series = this,
                chart = series.chart,
                points = series.points,
                {
                    animation
                } = series.options,
                {
                    startAngleRad
                } = series;

            function fanAnimate(point, startAngleRad) {
                const graphic = point.graphic,
                    args = point.shapeArgs;

                if (graphic && args) {

                    graphic
                        // Set inital animation values
                        .attr({
                            start: startAngleRad,
                            end: startAngleRad,
                            opacity: 1
                        })
                        // Animate to the final position
                        .animate({
                            start: args.start,
                            end: args.end
                        }, {
                            duration: animation.duration / points.length
                        }, function () {
                            // On complete, start animating the next point
                            if (points[point.index + 1]) {
                                fanAnimate(points[point.index + 1], args.end);
                            }
                            // On the last point, fade in the data labels, then
                            // apply the inner size
                            if (point.index === series.points.length - 1) {
                                series.dataLabelsGroup.animate({
                                        opacity: 1
                                    },
                                    void 0,
                                    function () {
                                        points.forEach(point => {
                                            point.opacity = 1;
                                        });
                                        series.update({
                                            enableMouseTracking: true
                                        }, false);
                                        chart.update({
                                            plotOptions: {
                                                pie: {
                                                    innerSize: '40%',
                                                    borderRadius: 8
                                                }
                                            }
                                        });
                                    });
                            }
                        });
                }
            }

            if (init) {
                // Hide points on init
                points.forEach(point => {
                    point.opacity = 0;
                });
            } else {
                fanAnimate(points[0], startAngleRad);
            }
        };
    }(Highcharts));


    let pollDataFromServer;
    pollDataFromServer = pollData;

    for (let question of pollDataFromServer.questions) {
        if (question.questionType === 'MultipleChoice') {

            // Generate the pie chart
            Highcharts.chart('pie-chart-' + question.id, {
                chart: {
                    type: 'pie',
                },
                title: {
                    text: 'Results',
                    align: 'left'
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: ' + this.point.y;
                    }
                },


                series: [{
                    enableMouseTracking: false,
                    animation: {
                        duration: 500
                    },
                    colorByPoint: true,
                    data: getChartDataForQuestion(question)
                }]
            });
        }
    }

});

function getChartDataForQuestion(question) {
    let data = [];

    for (let choice of question.possibleChoices) {
        let choiceData = {
            name: choice.answerChoice,
            y: 0  // Initialize the count to 0
        };

        for (let answer of question.answers) {
            if (answer.answerChoice === choice.answerChoice) {
                choiceData.y++;
            }
        }

        data.push(choiceData);
    }

    return data;
}


function updateChartData(chart, question) {
    let newData = getChartDataForQuestion(question);

    chart.series[0].setData(newData);
}