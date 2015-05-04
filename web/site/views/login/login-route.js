app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('/login', {
            url:'/login',
            templateUrl: 'views/login/login.html',
            controller: 'LoginController'
        });
    }]);