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

app.controller("LoginController", ['$scope', 'dialog', '$timeout', function($scope, dialog, $timeout) {

        $scope.dialog.open({
            title: "Nueva Ventana:" + Math.random()
        });

        $scope.recibidoPrevia = $scope.dialog.data;





        $scope.newDialog = function() {
            dialog.create(getContextPath() + "/test/modalmemory/modal.html", 10).then(function(resultado) {
                if (resultado === 20) {
                    $scope.newDialog();
                }
            });
        };

        if ($scope.recibidoPrevia === 10) {
            $timeout(function(){$scope.dialog.closeOK(20)}, 2000)
        } else {
            $scope.newDialog();
        }

    }]);