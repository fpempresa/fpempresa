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