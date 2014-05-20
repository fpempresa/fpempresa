
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/end/:tipoUsuario', {
            templateUrl: 'createaccount/end.html',
            controller: 'CreateAccountEndController'
        });
    }]);


app.controller('CreateAccountEndController', ['$scope', '$routeParams', 'goPage', function($scope, $routeParams, goPage) {
        $scope.model = {};
        $scope.model.tipoUsuario = $routeParams.tipoUsuario;

        $scope.login = function() {
            goPage.login();
        };

    }]);
