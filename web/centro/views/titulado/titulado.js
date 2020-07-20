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

app.controller("TituladoSearchController", ['$scope', '$http', 'ix3Configuration','dialog', function ($scope, $http, ix3Configuration,dialog) {
        $scope.businessMessages = [];
        
        $scope.fail = {};
        $scope.apiUrl = ix3Configuration.server.api;
        $scope.mostrarCodigosMunicipio = function () {
            dialog.create('mostrarCodigosMunicipio');
        };
        
        $scope.failImportJson = function (data) {
            if (data && data.jqXHR) {
                if (data.jqXHR.status === 500) {
                    alert("Uf, esto es embarazoso pero \n ha ocurrido un error al procesar la petición:\n"+data.jqXHR.responseText);
                } else {
                    if (data.jqXHR.responseText) {
                        $scope.businessMessages = JSON.parse(data.jqXHR.responseText);
                    }
                }
            }
        };
        $scope.updateList = function () {
            alert("El listado de Titulados se importó correctamente");
            $scope.search();
        };
        
        $scope.anyo=null;
        $scope.anyos=[];
        var currentYear=(new Date()).getFullYear();
        for(var anyo=currentYear;anyo>=1980;anyo--) {
            $scope.anyos.push(anyo);
        }        
        
        
        if ($scope.user && $scope.user.centro) {
            $scope.centro=$scope.user.centro;
            getEstadisticasByCentro($scope,$scope.user.centro,$scope.anyo,$scope.anyo);
        }
        

        
        function getEstadisticasByCentro($scope,centro,anyoInicio,anyoFin) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro + "?expand=titulosFPPorFamilia.tituladosPorCiclo&anyoInicio=" + (anyoInicio==null?'':anyoInicio) +"&anyoFin=" + (anyoFin==null?'':anyoFin)
            }).then(function (estadisticas) {
                $scope.centro.estadisticas = estadisticas.data;
            });
        } 
        
        $scope.buttonSearch=function() {
            getEstadisticasByCentro($scope,$scope.user.centro,$scope.anyo,$scope.anyo);
        }
        
    }]);
