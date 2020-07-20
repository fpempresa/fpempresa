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
