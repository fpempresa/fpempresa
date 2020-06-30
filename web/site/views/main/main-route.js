app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('/', {
            url:'/',
            templateUrl: 'views/main/main.html',
            controller: 'MainController'
        });
        $stateProvider.state('/ofertas', {
            url:'/ofertas',
            templateUrl: 'views/main/main.html',
            controller: 'MainController',
            scroll:"section-ultimas-ofertas"
        });        
    }]);