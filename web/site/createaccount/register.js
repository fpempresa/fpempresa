
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/register/:tipoUsuario', {
            templateUrl: 'createaccount/register.html',
            controller: 'CreateAccountRegisterController'
        });
    }]);


app.controller('CreateAccountRegisterController', ['$scope', '$routeParams', '$location', 'daoFactory', 'goPage', 'validator', function($scope, $routeParams, $location, daoFactory, goPage, validator) {
        var usuarioDAO = daoFactory.getDAO("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;
        $scope.model.tipoUsuario = $routeParams.tipoUsuario;
        
        $scope.registrarse = function() {
            $scope.businessMessages = validator.validateForm($scope.mainForm, [validateEqualPasswords, validateCheckCondicioneTerminos]);
            if ($scope.businessMessages.length === 0) {
                usuarioDAO.insert($scope.model, function() {
                    $location.path("/createaccount/end/" + $scope.model.tipoUsuario);
                }, function(error) {
                    if (error.status === 400) {
                        $scope.businessMessages = error.data;
                    } else {
                        $scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
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
