app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('/forbidden', {
            url:'/forbidden',
            templateUrl: 'forbidden/forbidden.html',
            controller: 'ForbiddenController'
        });
    }]);