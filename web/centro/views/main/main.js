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
        
        titulados: 186,
        empresas: 2,
        ofertas: 6,
        graficaOfertasPorFamilia: {
            title: "Titulados por familia",
            subtitle: null,
            yAxisTitle: "Nº de titulados",
            labels: [
                'Informática y Comunicaciones',
                'Comercio y Marketing',
                'Administración y Gestión',
                'Sanidad'
            ],
            series: [
                {
                    name: "Familias profesionales",
                    data: [
                        54, 45, 38, 49
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