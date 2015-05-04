app.config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('/docs/faq', {
            url:'/docs/faq',
            templateUrl: 'views/docs/faq.html'
        });
        $stateProvider.state('/docs/contacto', {
            url:'/docs/contacto',
            templateUrl: 'views/docs/contacto.html'
        });
        $stateProvider.state('/docs/privacidad', {
            url:'/docs/privacidad',
            templateUrl: 'views/docs/privacidad.html'
        });
        $stateProvider.state('/docs/terminos', {
            url:'/docs/terminos',
            templateUrl: 'views/docs/terminos.html'
        });
        $stateProvider.state('/docs/asociacion', {
            url:'/docs/asociacion',
            templateUrl: 'views/docs/asociacion.html'
        });
    }]);
