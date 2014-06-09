
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/register/:tipoUsuario', {
            templateUrl: 'createaccount/register.html',
            controller: 'CreateAccountRegisterController'
        });
    }]);


app.controller('CreateAccountRegisterController', ['$scope', '$routeParams', '$location', 'remoteServiceFactory', 'goPage', 'validator', function($scope, $routeParams, $location, remoteServiceFactory, goPage, validator) {
        var usuarioRemoteService = remoteServiceFactory.getRemoteService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;
        $scope.model.tipoUsuario = $routeParams.tipoUsuario;
        
        $scope.registrarse = function() {
            $scope.businessMessages = validator.validateForm($scope.mainForm, [validateEqualPasswords, validateCheckCondicioneTerminos]);
            if ($scope.businessMessages.length === 0) {
                usuarioRemoteService.insert($scope.model).then(function() {
                    $location.path("/createaccount/end/" + $scope.model.tipoUsuario);
                }, function(businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };


        function validateEqualPasswords() {
            if ($scope.model.password === $scope.model.confirmPassword) {
                return null;
            } else {
                return {
                    propertyName:"confirmPassword",
                    message: 'No es igual a la contraseña'
                };
            }
        }
        function validateCheckCondicioneTerminos() {
            if ($scope.model.aceptarCondicionesPolitica === true) {
                return null;
            } else {
                return {
                    label:"Condiciones y politicas",
                    message: 'Debe aceptar las condiciones del servicio y la politica de privacidad de FPempresa'
                };
            }
        }


    }]);
