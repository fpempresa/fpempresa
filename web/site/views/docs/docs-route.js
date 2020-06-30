app.config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('/docs/faq', {
            url:'/docs/faq',
            templateUrl: 'views/docs/faq.html'
        });
        $stateProvider.state('/docs/donde', {
            url:'/docs/donde',
            templateUrl: 'views/docs/donde.html'
        });
        $stateProvider.state('/docs/soporte', {
            url:'/docs/soporte',
            templateUrl: 'views/docs/soporte.html',
            controller: 'SoporteController'
        });               
    }]);
