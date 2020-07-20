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

    CambiarContrasenyaController.$inject = ['$scope', 'serviceFactory', 'formValidator','currentDialog'];
    function CambiarContrasenyaController($scope, serviceFactory, formValidator,currentDialog) {
        $scope.businessMessages = [];
        $scope.usuario=currentDialog.params;
        
        currentDialog.open({
            width: 550,
            height: 'auto',
            title: "Cambiar contraseña de " + $scope.usuario.name
        });



        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };

        $scope.buttonOK = function () {
            var usuarioService = serviceFactory.getService("Usuario");

            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioService.updatePassword($scope.usuario.idIdentity, $scope.model.currentPassword, $scope.model.newPassword).then(function () {
                    alert("Su contraseña ha sido cambiada correctamente");
                    currentDialog.closeOK();
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };


        $scope.$validators = [
            {
                message: "La nueva contraseña y confirmar contraseña deben coincidir",
                rule: function () {
                    return ($scope.model.newPassword === $scope.model.confirmPassword)
                }
            }
        ]

    }

    angular.module("common").controller("CambiarContrasenyaController", CambiarContrasenyaController);

    angular.module("common").config(['dialogProvider', 'getContextPath', function (dialogProvider, getContextPath) {
            
        dialogProvider.when('cambiarContrasenya', {
            templateUrl: getContextPath() + "/common/presentation/view/util/cambiarcontrasenya.html",
            controller: 'CambiarContrasenyaController'
        });
                    
    }]);

})();