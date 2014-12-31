app.controller('CreateAccountEndController', ['$scope', '$stateParams', 'goPage', function($scope, $stateParams, goPage) {
        $scope.model = {};
        $scope.model.tipoUsuario = $stateParams.tipoUsuario;

        $scope.login = function() {
            goPage.login();
        };

    }]);
