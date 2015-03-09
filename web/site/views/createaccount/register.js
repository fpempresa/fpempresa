
app.controller('CreateAccountRegisterController', ['$scope', '$stateParams', '$location', 'repositoryFactory', 'formValidator', function ($scope, $stateParams, $location, repositoryFactory, formValidator) {
        var usuarioRepository = repositoryFactory.getRepository("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;
        $scope.model.tipoUsuario = $stateParams.tipoUsuario;

        $scope.registrarse = function () {
            $scope.businessMessages = formValidator.validate($scope.mainForm,$scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioRepository.insert($scope.model).then(function () {
                    $location.path("/createaccount/end/" + $scope.model.tipoUsuario);
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };

        $scope.volver = function () {
            $location.path("/createaccount/init/" + $scope.model.tipoUsuario);
        }

    }]);
