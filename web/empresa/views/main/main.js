app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController'
        });
    }]);


MainController.$inject = ['$scope'];
function MainController($scope) {
    $scope.businessMessages = [];


    $scope.model = {
        ofertas: 15,
        candidatos: 91,
        usuarios: 2,
        graficaOfertasPorFamilia: {
            title: "Ofertas por familia",
            subtitle: null,
            yAxisTitle: "Nº de ofertas",
            labels: [
                'Informática y Comunicaciones',
                'Comercio y Marketing',
                'Administración y Gestión'
            ],
            series: [
                {
                    name: "Familias profesionales",
                    data: [
                        5, 8, 2
                    ]
                }
            ]
        }
    };

    showChart($("#grafica"), $scope.model.graficaOfertasPorFamilia);

}

function showChart(element, resultado) {
    if (element.highcharts()) {
        element.highcharts().destroy();
    }
    if (resultado === null) {
        return;
    }

    element.highcharts({
        chart: {
            type: 'column'
        },
        credits: {
            enabled: false
        },
        title: {
            text: resultado.title
        },
        subtitle: {
            text: resultado.subtitle
        },
        xAxis: {
            categories: resultado.labels
        },
        yAxis: {
            min: 0,
            title: {
                text: resultado.yAxisTitle
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    color: "#000000",
                    style: {
                        fontWeight: 'bold'
                    },
                    formatter: function () {
                        return this.y;
                    }
                }
            }
        },
        series: [{
                name: resultado.series[0].name,
                data: resultado.series[0].data

            }],
        exporting: {
            enabled: false
        }
    });



}
app.controller("MainController", MainController);