app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('lateralmenu.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController',
            resolve: {
            }
        });
    }]);