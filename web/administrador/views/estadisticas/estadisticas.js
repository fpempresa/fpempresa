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
        $scope.indexCentroActual=0;
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Centro"
        }).then(function (centros) {
            $scope.centros = centros.data.splice(1);
            if ($scope.centros.length>0) {
                getEstadisticasByCentro($scope,$scope.centros[$scope.indexCentroActual]);
            }
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        function getEstadisticasByCentro($scope,centro) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro + "?expand=titulosFPPorFamilia.tituladosPorCiclo"
            }).then(function (estadisticas) {
                centro['estadisticas'] = estadisticas.data;
                $scope.indexCentroActual++;
                if ($scope.indexCentroActual<$scope.centros.length) {
                    //Los llamamos uno a uno para no sobrecargar al servidor con tantas peticiones a la vez
                    getEstadisticasByCentro($scope,$scope.centros[$scope.indexCentroActual]);
                }
            });
        }
        
       


    }]);
