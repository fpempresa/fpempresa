
app.controller('CreateAccountInitController', ['$scope', '$location','$routeParams', 'repositoryFactory', 'formValidator', function ($scope, $location, $routeParams, repositoryFactory, formValidator) {
        var usuarioRepository = repositoryFactory.getRepository("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioRepository.create().then(function (usuario) {
            $scope.model.tipoUsuario = usuario.tipoUsuario;
            if ($routeParams.tipoUsuario) {
                //Sobreescribimos el valor si nos los pasan en la URL
                $scope.model.tipoUsuario = $routeParams.tipoUsuario;
            }
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
