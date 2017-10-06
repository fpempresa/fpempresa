"use strict";

app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.estadisticas', {
            url: "/estadisticas/centros",
            templateUrl: 'views/estadisticas/centro.html',
            controller: 'EstadisticasCentroController'
        });
    }]);



app.controller("EstadisticasCentroController", ['$scope', '$http', 'ix3Configuration', function ($scope, $http, ix3Configuration) {
        $scope.businessMessages = [];
        $scope.centros = [];
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Centro"
        }).then(function (centros) {
            $scope.centros = centros.data;
            centros.data.forEach(getEstadisticasByCentro);
            $scope.centros.splice(0, 1);
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        function getEstadisticasByCentro(centro) {
            console.log(centro);
            if (centro.idCentro > 0) {
                $http({
                    method: "GET",
                    url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro
                }).then(function (estadisticas) {
                    centro['estadisticas'] = estadisticas.data;
                    estadisticas.data.tituladosPorFamilia.forEach(getEstadisticasByFamilia);
                });
            }
        }
        
        function getEstadisticasByFamilia(familia) {
            
        }


    }]);
