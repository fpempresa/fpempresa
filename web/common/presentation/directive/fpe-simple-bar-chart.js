/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
"use strict";

angular.module("common").directive('fpeSimpleBarChart', [function () {
        return {
            restrict: 'A',
            scope: {
                title: "@",
                subtitle: "@",
                titleY: "@",
                titleX: "@",
                serie: "="
            },
            link: function ($scope, element, attributes) {

                var chart = null;
                var labels = [];
                var data = [];

                $scope.$watch("serie", function (newSerie, oldSerie) {

                    if (newSerie === oldSerie) {
                        return;
                    }

                    while (labels.length > 0) {
                        data.pop();
                    }
                    while (data.length > 0) {
                        data.pop();
                    }

                    if (newSerie === null) {
                        if (chart !== null) {
                            chart.update();
                        } else {
                            return;
                        }
                    }

                    var titleX = $scope.titleX;
                    var title = $scope.title;
                    var titleY = $scope.titleY;



                    for (var i = 0; i < $scope.serie.length; i++) {
                        labels.push($scope.serie[i].$toString);
                        data.push($scope.serie[i].valor);
                    }


                    if (chart !== null) {
                        chart.update();
                    } else {
                        chart = new Chart(element,
                                {
                                    type: "bar",
                                    data: {
                                        labels: labels,
                                        datasets: [
                                            {
                                                data: data,
                                                fill: false,
                                                backgroundColor: "#428bca",
                                                datalabels: {
                                                    color: "#000000",
                                                    anchor: "end",
                                                    align: "top",
                                                    clamp: true
                                                }
                                            }
                                        ]
                                    },
                                    options: {
                                        title: {
                                            display: true,
                                            text: title
                                        },
                                        legend: {
                                            display: false
                                        },
                                        scales: {
                                            xAxes: [
                                                {
                                                    scaleLabel: {
                                                        display: true,
                                                        labelString: titleX,
                                                        padding: {
                                                            top:10,
                                                            bottom:0
                                                        },
                                                        fontSize:14,
                                                        fontStyle:"bold"
                                                    },
                                                    ticks: {
                                                        // Include a dollar sign in the ticks
                                                        callback: function (value, index, values) {
                                                            var max=16;
                                                            var label=""+value;
                                                            if (label.length>max) {
                                                                label=label.substr(0,max)+"...";
                                                            }
                                                            
                                                            return label;
                                                        }
                                                    }
                                                }
                                            ],
                                            yAxes: [
                                                {
                                                    scaleLabel: {
                                                        display: true,
                                                        labelString: titleY,
                                                        fontSize:14,
                                                        fontStyle:"bold"
                                                    },
                                                    ticks: {
                                                        beginAtZero: true
                                                    }
                                                }
                                            ]

                                        }
                                    }
                                }

                        );
                    }



                })


            }
        };
    }]);

 