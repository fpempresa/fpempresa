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
                    notify.info(undefined,"Su mensaje ha sido enviado correctamente",4000);
                    $scope.model = {};
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };
    }]);
