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

app.controller("ResetearContrasenyaController", ['$scope', 'formValidator', 'serviceFactory', '$stateParams', '$state', function ($scope, formValidator, serviceFactory, $stateParams, $state) {
        var usuarioService = serviceFactory.getService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;
        $scope.$validators = [{
                message: "La nueva contraseña y confirmar contraseña deben coincidir",
                rule: function () {
                    return ($scope.model.password === $scope.model.confirmPassword)
                }
            }];
        $scope.aceptar = function () {
            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if (!$stateParams.token) {
                $scope.businessMessages = 'No se ha especificado ningún token de seguridad';
            } else {
                if ($scope.businessMessages.length === 0) {
                    usuarioService.resetearContrasenya($stateParams.token, $scope.model.password).then(function () {
                        alert("Su contraseña se ha cambiado correctamente.")
                        $state.go('/');
                    }, function (businessMessages) {
                        $scope.businessMessages = businessMessages;
                    });
                }
            }
        };
    }]);