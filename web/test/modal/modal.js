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

app.controller("LoginController", ['$scope', 'dialog', function($scope, dialog) {

        $scope.dialog.open({
            title:"Nueva Ventana:" + Math.random()
        });
        
        $scope.recibidoPrevia=$scope.dialog.data;

        $scope.devolver = function() {
                $scope.dialog.closeOK($scope.devuelto);
        };

        $scope.cancel = function() {
            $scope.dialog.closeCancel(undefined);
        };

        $scope.newDialog = function() {
            dialog.create(getContextPath()+"/test/modal/modal.html",$scope.enviado).then(function(resultado){
                if (resultado) {
                    $scope.recibidoSiguiente=resultado;
                }
            });
        };

    }]);