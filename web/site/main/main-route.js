app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('/', {
            url:'/',
            templateUrl: 'main/main.html',
            controller: 'MainController'
        });
    }]);