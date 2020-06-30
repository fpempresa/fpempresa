app.controller('SoporteController', ['$scope', '$location', '$stateParams', 'serviceFactory', 'formValidator','notify', function ($scope, $location, $stateParams, serviceFactory, formValidator,notify) {
        var usuarioService = serviceFactory.getService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        $scope.$validators = [
            {
                message: "Debe aceptar la política de privacidad",
                rule: function () {
                    return ($scope.model.aceptadoLOPD === true)
                }
            }
        ]

        $scope.enviarMensajeSoporte = function () {
            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioService.enviarMensajeSoporte($scope.model).then(function () {
                    alert("El soporte ha recibido tu mensaje")
                    $scope.model = {};
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };
    }]);
