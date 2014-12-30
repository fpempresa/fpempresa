app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('cols1.forbidden', {
            url: "/forbidden",
            templateUrl: 'views/util/forbidden/forbidden.html',
            controller: 'ForbiddenController'
        });
    }]);