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