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

app.config(['crudRoutesProvider', '$stateProvider', function (crudRoutesProvider, $stateProvider) {
        $stateProvider.state('lateralmenu.configuracion', {
            url: "/configuracion",
            templateUrl: 'views/configuracion/detail.html',
            controller: 'ConfiguracionTituladoController',
            resolve: crudRoutesProvider.getResolve("Titulado","configuracion.notificacionOferta.provincias","EDIT") 
        });        
    }]);

app.controller("ConfiguracionTituladoController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'serviceFactory', function ($scope, genericControllerCrudDetail, controllerParams, serviceFactory) {
        if ($scope.user.titulado) {
            controllerParams.id=$scope.user.titulado.idTitulado;
        }
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        
        $scope.finishOK = function () {
            alert("La configuración ha sido modificada correctamente");
        };
        
        
        $scope.checkTodasProvincias = function (todasProvincias) {

            if (todasProvincias) {
                $scope.model.configuracion.notificacionOferta.provincias = angular.copy($scope.provincias);
            } else {
                $scope.model.configuracion.notificacionOferta.provincias = [];
            }

        };


        var serviceProvincia = serviceFactory.getService("Provincia");
        var query = {
            filters: {
                
            },
            orderby: [
                {fieldName: "descripcion", orderDirection: "ASC"}
            ]
        }

        serviceProvincia.search(query).then(function (provincias) {
            $scope.provincias = provincias;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });


        $scope.compareProvincia = function (provinciaA, provinciaB) {
            if (provinciaA && provinciaB) {
                return (provinciaA.idProvincia === provinciaB.idProvincia);
            } else {
                return false;
            }
        }        
        
    }]);
