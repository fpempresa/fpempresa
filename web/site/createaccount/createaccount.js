
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount', {
            templateUrl: 'createaccount/createaccount.html',
            controller: 'CreateAccountController'
        });
    }]);


app.controller('CreateAccountController', ['$scope', function($scope) {
        
}]);
