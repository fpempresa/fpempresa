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

app.controller("ConfiguracionTituladoController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'serviceFactory', '$timeout', function ($scope, genericControllerCrudDetail, controllerParams, serviceFactory, $timeout) {
        if ($scope.user.titulado) {
            controllerParams.id=$scope.user.titulado.idTitulado;
        }
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        
        $scope.finishOK = function () {
            alert("La configuración ha sido modificada correctamente");
        };
        
        
        $scope.checkTodasProvincias = function () {
            $timeout(function() {
                if ($scope.todasProvincias) {
                    $scope.model.configuracion.notificacionOferta.provincias = angular.copy($scope.provincias);
                } else {
                    $scope.model.configuracion.notificacionOferta.provincias = [];
                }
            }, 0);
        };


        var serviceProvincia = serviceFactory.getService("Provincia");
        var query = {
            filters: {
                
            },
            orderby: [
                {fieldName: "descripcion", orderDirection: "ASC"}
            ]
        }

        function existsProvincia(provincias,provincia) {
            for(var i=0;i<provincias.length;i++) {
                if ($scope.compareProvincia(provincias[i],provincia)) {
                    return true;
                }
            }            
            
            return false;
        }
        
        function equalsProvincias(provinciasA,provinciasB) {
            if (!(Array.isArray(provinciasA) && Array.isArray(provinciasB))) {
                return false;
            }
            
            if (provinciasA.length!==provinciasB.length) {
                return false;
            }
            
            for(var i=0;i<provinciasA.length;i++) {
                if (existsProvincia(provinciasB,provinciasA[i])===false) {
                    return false;
                }
            }
            
            return true;
            
        }
                
        
        $scope.$watchCollection("model.configuracion.notificacionOferta.provincias", function (newProvincias, oldProvincias) {        
            if (newProvincias === oldProvincias) {
                return;
            }
            
            if (equalsProvincias(newProvincias,$scope.provincias)) {
                $scope.todasProvincias=true;
            } else {
                $scope.todasProvincias=false;
            }

        });


        serviceProvincia.search(query).then(function (provincias) {
            $scope.provincias = provincias;
            
            if (equalsProvincias($scope.model.configuracion.notificacionOferta.provincias,$scope.provincias)) {
                $scope.todasProvincias=true;
            } else {
                $scope.todasProvincias=false;
            }
            
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
