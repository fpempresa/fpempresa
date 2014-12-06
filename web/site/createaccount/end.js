app.controller('CreateAccountEndController', ['$scope', '$routeParams', 'goPage', function($scope, $routeParams, goPage) {
        $scope.model = {};
        $scope.model.tipoUsuario = $routeParams.tipoUsuario;

        $scope.login = function() {
            goPage.login();
        };

    }]);
