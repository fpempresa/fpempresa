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
        
        var currentYear=(new Date()).getFullYear();
        
        $scope.anyos=[];
        for(var anyo=currentYear;anyo>=1980;anyo--) {
            $scope.anyos.push(anyo);
        } 
        $scope.anyo=currentYear;
        
        
        $scope.$watch("anyo",function(newValue,oldValue) {            
            if ($scope.user && $scope.user.centro) {
                $scope.centro=$scope.user.centro;
                getEstadisticasByCentro($scope,$scope.user.centro,$scope.anyo,$scope.anyo);
            }
        });
        
        function getEstadisticasByCentro($scope,centro,anyoInicio,anyoFin) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro + "?expand=titulosFPPorFamilia.tituladosPorCiclo&anyoInicio=" + (anyoInicio==null?'':anyoInicio) +"&anyoFin=" + (anyoFin==null?'':anyoFin)
            }).then(function (estadisticas) {
                $scope.centro.estadisticas = estadisticas.data;
            });
        } 

        
    }]);
