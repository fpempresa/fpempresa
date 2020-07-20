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

app.controller('CreateAccountInitController', ['$scope', '$location', '$stateParams', 'serviceFactory', 'formValidator', function ($scope, $location, $stateParams, serviceFactory, formValidator) {
        var usuarioService = serviceFactory.getService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioService.create().then(function (usuario) {
            $scope.model = usuario;
            if ($stateParams.tipoUsuario) {
                //Sobreescribimos el valor si nos los pasan en la URL
                $scope.model.tipoUsuario = $stateParams.tipoUsuario;
            }


        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.registrarse = function () {
            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                
                switch ($scope.model.tipoUsuario) {
                    case "CENTRO":
                        $scope.model.estadoUsuario=null;
                        break;
                    case "TITULADO":
                        $scope.model.estadoUsuario="ACEPTADO";
                        break;
                    case "EMPRESA":
                        $scope.model.estadoUsuario="ACEPTADO";
                        break;
                    default:
                        throw Error("Tipo de usuario desonocido:" + $scope.model.tipoUsuario);
                }                
                
                
                usuarioService.insert($scope.model).then(function () {
                    $location.path("/createaccount/end/" + $scope.model.tipoUsuario);
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };


    }]);
