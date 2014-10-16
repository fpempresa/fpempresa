
app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/createaccount/init', {
            templateUrl: 'createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
    }]);


app.controller('CreateAccountInitController', ['$scope', '$location', 'remoteServiceFactory', 'formValidator', function ($scope, $location, remoteServiceFactory, formValidator) {
        var usuarioRemoteService = remoteServiceFactory.getRemoteService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioRemoteService.create().then(function (usuario) {
            $scope.model.tipoUsuario = usuario.tipoUsuario;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.next = function () {

            $scope.businessMessages = formValidator.validate($scope.mainForm, [validateDeshabilitadoCentro, validateDeshabilitadoEmpresa]);
            if ($scope.businessMessages.length === 0) {
                $location.path("/createaccount/register/" + $scope.model.tipoUsuario);
            }

        };

        function validateDeshabilitadoCentro() {
            if ($scope.model.tipoUsuario === "CENTRO") {
                return {
                    message: 'El registro de Centros Educativos no está habilitado'
                };
            } else {
                return null;
            }
        }
        function validateDeshabilitadoEmpresa() {
            if ($scope.model.tipoUsuario === "EMPRESA") {
                return {
                    message: 'El registro de Empresas no está habilitado'
                };
            } else {
                return null;
            }
        }

    }]);
