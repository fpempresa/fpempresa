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
        ofertas: 42,
        titulados: 930,
        empresas: 40,
        graficaOfertasPorFamilia: {
            title: "Ofertas por familia",
            subtitle: null,
            yAxisTitle: "Nº de ofertas",
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
                        9,12,8,13
                    ]
                }
            ]
        },
        graficaTituladosPorFamilia: {
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
                        234,321,156,219
                    ]
                }
            ]
        }        
    };

    showChart($("#graficaOfertas"), $scope.model.graficaOfertasPorFamilia);
    showChart($("#graficaTitulados"), $scope.model.graficaTituladosPorFamilia);

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