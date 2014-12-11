
app.controller('CreateAccountRegisterController', ['$scope', '$routeParams', '$location', 'repositoryFactory', 'formValidator', function($scope, $routeParams, $location, repositoryFactory, formValidator) {
        var usuarioRepository = repositoryFactory.getRepository("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;
        $scope.model.tipoUsuario = $routeParams.tipoUsuario;
        
        $scope.registrarse = function() {
            $scope.businessMessages = formValidator.validate($scope.mainForm, [validateEqualPasswords, validateCheckCondicioneTerminos]);
            if ($scope.businessMessages.length === 0) {
                usuarioRepository.insert($scope.model).then(function() {
                    $location.path("/createaccount/end/" + $scope.model.tipoUsuario);
                }, function(businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };

        $scope.volver = function() {
            $location.path("/createaccount/init/" + $scope.model.tipoUsuario);
        }

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
                    message: 'Debe aceptar los terminos del servicio y la política de privacidad de FPempresa'
                };
            }
        }


    }]);
