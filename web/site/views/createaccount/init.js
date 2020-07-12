
app.controller('CreateAccountInitController', ['$scope', '$location', '$stateParams', 'serviceFactory', 'formValidator', function ($scope, $location, $stateParams, serviceFactory, formValidator) {
        var usuarioService = serviceFactory.getService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioService.create().then(function (usuario) {
            $scope.model = usuario;
            if ($stateParams.tipoUsuario) {
                //Sobreescribimos el valor si nos los pasan en la URL
                $scope.model.tipoUsuario = $stateParams.tipoUsuario;
            }


        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.registrarse = function () {
            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                
                switch ($scope.model.tipoUsuario) {
                    case "CENTRO":
                        $scope.model.estadoUsuario=null;
                        break;
                    case "TITULADO":
                        $scope.model.estadoUsuario="ACEPTADO";
                        break;
                    case "EMPRESA":
                        $scope.model.estadoUsuario="ACEPTADO";
                        break;
                    default:
                        throw Error("Tipo de usuario desonocido:" + $scope.model.tipoUsuario);
                }                
                
                
                usuarioService.insert($scope.model).then(function () {
                    $location.path("/createaccount/end/" + $scope.model.tipoUsuario);
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };


    }]);
