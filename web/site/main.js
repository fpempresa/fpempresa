app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'main.html',
            controller: 'MainController'
        });
    }]);


app.controller('MainController', ['$scope', function($scope) {
        
}]);

