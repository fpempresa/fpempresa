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
(function (undefined) {
    "use strict";

    EnviarMailResetearContrasenyaController.$inject = ['$scope', 'serviceFactory', 'formValidator'];
    function EnviarMailResetearContrasenyaController($scope, serviceFactory, formValidator) {
        $scope.businessMessages = [];



        $scope.cambiarContrasenya = function () {
            var usuarioService = serviceFactory.getService("Usuario");
            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioService.enviarMailResetearContrasenya($scope.userEmail).then(function () {
                    alert("Se ha enviado el correo con la información para cambiar su contraseña.");
                    $scope.userEmail="";
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };

    }

    angular.module("common").controller("EnviarMailResetearContrasenyaController", EnviarMailResetearContrasenyaController);



})();