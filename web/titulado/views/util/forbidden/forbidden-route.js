app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('forbidden', {
            url: "/forbidden",
            templateUrl: 'views/util/forbidden/forbidden.html',
            controller: 'ForbiddenController'
        });
    }]);