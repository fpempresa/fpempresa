app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'main.html',
            controller: 'MainController'
        });
    }]);


app.controller('MainController', ['$scope', 'goPage', function ($scope, goPage) {
        $scope.createAccount = function (tipoUsuario) {
            goPage.createAccount(tipoUsuario);
        };

        $scope.login = function () {
            goPage.login();
        };

    }]);



