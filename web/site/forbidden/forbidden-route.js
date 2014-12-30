app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/forbidden', {
            templateUrl: 'forbidden/forbidden.html',
            controller: 'ForbiddenController'
        });
    }]);